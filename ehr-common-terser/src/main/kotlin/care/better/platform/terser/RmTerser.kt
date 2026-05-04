/* Copyright 2026 Better Ltd (www.better.care)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package care.better.platform.terser

import care.better.openehr.rm.RmObject
import care.better.platform.path.NameAndNodeMatchingPathValueExtractor
import care.better.platform.path.PathSegment
import care.better.platform.path.PathUtils
import care.better.platform.path.PathValueExtractorException
import care.better.platform.utils.RmUtils
import org.openehr.rm.common.Locatable
import org.openehr.rm.composition.Action
import org.openehr.rm.composition.Activity
import org.openehr.rm.composition.AdminEntry
import org.openehr.rm.composition.Evaluation
import org.openehr.rm.composition.Instruction
import org.openehr.rm.composition.Observation
import org.openehr.rm.composition.Section
import org.openehr.rm.datastructures.Cluster
import org.openehr.rm.datastructures.Element
import org.openehr.rm.datastructures.Event
import org.openehr.rm.datastructures.History
import org.openehr.rm.datastructures.ItemList
import org.openehr.rm.datastructures.ItemSingle
import org.openehr.rm.datastructures.ItemTable
import org.openehr.rm.datastructures.ItemTree
import org.openehr.rm.datatypes.DvIdentifier

/**
 * @author Primoz Delopst
 * @since 4.3.1
 *
 * Provides access to RM objects using AQL paths.
 *
 * @constructor Creates a new instance of [RmTerser]
 * @param rmObject [RmObject]
 */
class RmTerser(private val rmObject: RmObject) {

    /**
     * Extracts and returns [List] of values at the given AQL path.
     *
     * @param path AQL path [String]
     * @return [List] of extracted values
     */
    fun getValues(path: String): List<Any> =
        NameAndNodeMatchingPathValueExtractor(path, false).getValue(rmObject)

    /**
     * Extracts and returns a single value at the given AQL path, or null if not found.
     *
     * @param path AQL path [String]
     * @return Extracted value or null
     * @throws IllegalArgumentException if multiple values are found at the given path
     */
    fun getValue(path: String): Any? =
        getValues(path).also { require(it.size <= 1) { "Expected at most one value at path '$path', but found ${it.size}" } }.firstOrNull()

    /**
     * Sets the value at the given AQL path on all matching parent nodes.
     *
     * For scalar properties, invokes the setter method with the new value.
     * For collection properties matched by archetype node ID/name, replaces the matching element.
     *
     * @param path AQL path [String]
     * @param value Value to set (or null to clear)
     */
    fun setValue(path: String, value: Any?) {
        withLastSegment(path) { parents, segment, property ->
            parents.forEach { parent ->
                val current = invokeGetter(parent, property)
                if (current is MutableList<*>) {
                    @Suppress("UNCHECKED_CAST")
                    val list = current as MutableList<Any?>
                    require(segment.archetypeNodeId != null) { "Cannot set value on collection without archetype node ID predicate. Use addValue to add to collections." }
                    val index = list.indexOfFirst { it != null && elementMatches(it, segment) }
                    require(index >= 0) { "No matching element found at path '$path'. Use addValue to add new elements to collections." }
                    list[index] = value
                } else {
                    invokeSetter(parent, property, value)
                }
            }
        }
    }

    /**
     * Adds a value to a collection at the given AQL path on all matching parent nodes.
     *
     * @param path AQL path [String]
     * @param value Value to add
     */
    fun addValue(path: String, value: Any?) {
        withLastSegment(path) { parents, _, property ->
            parents.forEach { parent ->
                val current = invokeGetter(parent, property)
                require(current is MutableList<*>) { "Cannot add value to non-collection property at path '$path'. Use setValue for scalar properties." }
                @Suppress("UNCHECKED_CAST")
                (current as MutableList<Any?>).add(value)
            }
        }
    }

    /**
     * Removes values at the given AQL path.
     *
     * For collection items matched by archetype node ID/name, removes the matching elements from the collection.
     * For scalar properties, sets the property to null.
     * After removal, recursively removes empty parent nodes up the path.
     *
     * @param path AQL path [String]
     */
    fun removeValue(path: String) {
        val segments = PathUtils.getPathSegments(path).also { require(it.isNotEmpty()) { "Path must not be empty" } }
        val lastSegment = segments.last()
        val property = PathUtils.getPropertyName(lastSegment.element)
        val parents = navigateToParents(segments)

        parents.forEach { parent ->
            val current = invokeGetter(parent, property)
            if (current is MutableList<*> && lastSegment.archetypeNodeId != null) {
                @Suppress("UNCHECKED_CAST")
                (current as MutableList<Any>).removeAll { elementMatches(it, lastSegment) }
            } else {
                invokeSetter(parent, property, null)
            }
        }

        cleanupEmptyParents(segments)
    }

    private inline fun withLastSegment(path: String, action: (List<Any>, PathSegment, String) -> Unit) {
        val segments = PathUtils.getPathSegments(path).also { require(it.isNotEmpty()) { "Path must not be empty" } }
        action(navigateToParents(segments), segments.last(), PathUtils.getPropertyName(segments.last().element))
    }

    private fun navigateToParents(segments: List<PathSegment>): List<Any> =
        if (segments.size == 1) listOf(rmObject)
        else NameAndNodeMatchingPathValueExtractor(segments.dropLast(1).joinToString("/") { it.asPathSegment() }, false).getValue(rmObject)

    @Suppress("UNCHECKED_CAST")
    private fun invokeGetter(node: Any, propertyName: String): Any? =
        if (node is RmObject) RmUtils.getGetterForField(propertyName, node.javaClass as Class<out RmObject>)?.invoke(node)
        else propertyName.replaceFirstChar { it.uppercaseChar() }.let { name ->
            arrayOf("get", "is", "").firstNotNullOfOrNull { prefix ->
                node.javaClass.methods.firstOrNull { it.name == "$prefix$name" && it.parameterCount == 0 }
            }?.invoke(node) ?: throw PathValueExtractorException("No getter found for property '$propertyName' on ${node.javaClass.name}")
        }

    @Suppress("UNCHECKED_CAST")
    private fun invokeSetter(node: Any, propertyName: String, value: Any?) {
        if (node is RmObject) {
            RmUtils.getSetterForField(propertyName, node.javaClass as Class<out RmObject>)?.invoke(node, value)
            return
        }
        val methodName = "set${propertyName.replaceFirstChar { it.uppercaseChar() }}"
        (node.javaClass.methods.firstOrNull { it.name == methodName && it.parameterCount == 1 }
            ?: throw PathValueExtractorException("No setter found for property '$propertyName' on ${node.javaClass.name}"))
            .invoke(node, value)
    }

    private fun cleanupEmptyParents(segments: List<PathSegment>) {
        for (i in segments.size - 2 downTo 0) {
            val segment = segments[i]
            val property = PathUtils.getPropertyName(segment.element)
            val parents = navigateToParents(segments.subList(0, i + 1))

            parents.forEach { parent ->
                val current = invokeGetter(parent, property)
                if (current is MutableList<*>) {
                    @Suppress("UNCHECKED_CAST")
                    val list = current as MutableList<Any>
                    if (segment.archetypeNodeId != null) {
                        list.removeAll { elementMatches(it, segment) && it is RmObject && isRmObjectEmpty(it) }
                    }
                } else if (current is RmObject && isRmObjectEmpty(current)) {
                    invokeSetter(parent, property, null)
                }
            }
        }
    }

    private fun isRmObjectEmpty(rmObject: RmObject?): Boolean =
        when (rmObject) {
            is Event -> isRmObjectEmpty(rmObject.data) && isRmObjectEmpty(rmObject.state)
            is ItemTree -> rmObject.items.isEmpty()
            is ItemList -> rmObject.items.isEmpty()
            is ItemSingle -> rmObject.item == null
            is ItemTable -> rmObject.rows.isEmpty()
            is History -> rmObject.events.isEmpty() && isRmObjectEmpty(rmObject.summary)
            is Section -> rmObject.items.isEmpty()
            is Observation -> isRmObjectEmpty(rmObject.data) && isRmObjectEmpty(rmObject.state) && isRmObjectEmpty(rmObject.protocol)
            is Evaluation -> isRmObjectEmpty(rmObject.data)
            is Instruction -> rmObject.activities.isEmpty() && isRmObjectEmpty(rmObject.protocol)
            is Action -> isRmObjectEmpty(rmObject.description)
            is Activity -> isRmObjectEmpty(rmObject.description)
            is AdminEntry -> isRmObjectEmpty(rmObject.data)
            is Cluster -> rmObject.items.isEmpty()
            is DvIdentifier -> rmObject.id.isNullOrBlank()
            is Element -> rmObject.value == null && rmObject.nullFlavour == null
            else -> rmObject == null
        }

    private fun elementMatches(node: Any, segment: PathSegment): Boolean =
        node !is Locatable ||
            ((segment.archetypeNodeId == null || segment.archetypeNodeId == node.archetypeNodeId) &&
                (segment.name == null || when {
                    segment.prefix == null -> segment.name == node.name?.value
                    "uid/value".equals(segment.prefix, ignoreCase = true) -> segment.name == node.uid?.value
                    else -> false
                }))
}
