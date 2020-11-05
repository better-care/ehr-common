/* Copyright 2021 Better Ltd (www.better.care)
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

@file:JvmName("AmUtils")

package care.better.platform.template

import care.better.platform.path.PathSegment
import care.better.platform.path.PathUtils
import care.better.platform.template.exception.AmException
import org.openehr.am.aom.*
import org.openehr.base.foundationtypes.IntervalOfInteger
import org.openehr.rm.common.Locatable
import org.openehr.rm.common.StringDictionaryItem
import org.openehr.rm.datatypes.DvCodedText
import org.openehr.rm.datatypes.DvText
import java.util.*
import java.util.regex.Pattern

/**
 * @author Bostjan Lah
 * @author Primoz Delopst
 * @since 3.1.0
 *
 * Set of utilities for [AmNode] building and retrieving.
 */
@Suppress("MemberVisibilityCanBePrivate")
object AmUtils {
    const val VALUE_ATTRIBUTE = "value"
    const val NAME_ATTRIBUTE = "name"
    const val CODE_PHRASE_ATTRIBUTE = "defining_code"
    const val TEXT_ID = "text"
    const val DESCRIPTION_ID = "description"
    val NAME_SUFFIX: Pattern = Pattern.compile("\\s*#[0-9]+\\s*$")

    /**
     * Create a new instance of [IntervalOfInteger].
     *
     * @param lower Interval lower bound
     * @param upper Interval upper bound
     * @return [IntervalOfInteger]
     */
    @JvmStatic
    fun createInterval(lower: Int, upper: Int?) =
        IntervalOfInteger().apply {
            this.lower = lower
            this.upper = upper
            this.lowerIncluded = true
            this.upperIncluded = true
        }

    /**
     * Finds and returns [AmNode] for path segments.
     *
     * @param amNode [AmNode]
     * @param pathSegments Path segments
     * @return [AmNode] if found, otherwise, null
     */
    @JvmStatic
    fun getAmNode(amNode: AmNode, vararg pathSegments: String): AmNode? =
        with(getAmNodes(amNode, *pathSegments)) {
            if (this.isEmpty()) null else this.iterator().next()
        }

    /**
     * Finds and returns [List] of [AmNode] for path segments.
     *
     * @param amNode [AmNode]
     * @param pathSegments Path segments
     * @return [List] of [AmNode]
     */
    @JvmStatic
    fun getAmNodes(amNode: AmNode, vararg pathSegments: String): List<AmNode> =
        if (pathSegments.isEmpty())
            listOf(amNode)
        else
            getAmNodesRecursively(listOf(amNode), pathSegments.toList(), 0)

    private tailrec fun getAmNodesRecursively(amNodes: List<AmNode>, pathSegments: List<String>, index: Int): List<AmNode> {
        if (amNodes.isEmpty()) {
            return emptyList()
        }

        val pathSegment = pathSegments[index]
        val amNode = getAmNodeWithAttribute(amNodes, pathSegment)
        val children = amNode?.attributes?.get(pathSegment)?.children ?: emptyList()
        return if (index == pathSegments.size - 1) children else getAmNodesRecursively(children, pathSegments, index + 1)
    }

    /**
     * Finds and returns [AmNode] that contains attribute.
     *
     * @param amNodes [Collection] of [AmNode]
     * @param attributeName Name of the attribute
     * @return [AmNode] if found, otherwise, null
     */
    @JvmStatic
    fun getAmNodeWithAttribute(amNodes: Collection<AmNode>, attributeName: String): AmNode? =
        amNodes.firstOrNull { it.attributes.containsKey(attributeName) }

    /**
     * Finds and returns [ArchetypeTerm] for code.
     *
     * @param terms [Collection] of [ArchetypeTerm]
     * @param code Term code
     * @return [ArchetypeTerm] if found, otherwise, null
     */
    @JvmStatic
    fun findTerm(terms: Collection<ArchetypeTerm>, code: String?): ArchetypeTerm? = if (code == null) null else terms.firstOrNull { it.code == code }

    /**
     * Finds and returns [ArchetypeTerm] dictionary item value.
     *
     * @param terms [Collection] of [ArchetypeTerm]
     * @param nodeId Node ID
     * @param id Id
     * @return [StringDictionaryItem] value if found, otherwise, null
     */
    @JvmStatic
    fun findTerm(terms: Collection<ArchetypeTerm>, nodeId: String?, id: String): String? = findTerm(terms, nodeId)?.let { findDictionaryItem(it, id) }

    /**
     * Finds and returns [ArchetypeTerm] dictionary item value.
     *
     * @param term [ArchetypeTerm]
     * @param id ID
     * @return [StringDictionaryItem] value if found, otherwise, null
     */
    @JvmStatic
    fun findDictionaryItem(term: ArchetypeTerm, id: String): String? =
        term.items.asSequence().filter { id == it.id }.map { it.value }.firstOrNull()

    /**
     * Finds and returns [ArchetypeTerm] text.
     *
     * @param amNode [AmNode]
     * @param language Language
     * @param archetypeNodeId Archetype node ID
     * @return [ArchetypeTerm] text
     */
    @JvmStatic
    fun findText(amNode: AmNode, language: String, archetypeNodeId: String?): String? = findTermText(amNode, language, archetypeNodeId, TEXT_ID)

    /**
     * Finds and returns [ArchetypeTerm] description.
     *
     * @param amNode [AmNode]
     * @param language Language
     * @param archetypeNodeId Archetype node ID
     * @return [ArchetypeTerm] description
     */
    @JvmStatic
    fun findDescription(amNode: AmNode, language: String, archetypeNodeId: String?): String? = findTermText(amNode, language, archetypeNodeId, DESCRIPTION_ID)

    /**
     * Finds and returns [ArchetypeTerm] text.
     *
     * @param amNode [AmNode]
     * @param archetypeNodeId Archetype node ID
     * @return [ArchetypeTerm] text
     */
    @JvmStatic
    fun findTermText(amNode: AmNode, archetypeNodeId: String?): String? = findTerm(amNode.terms, archetypeNodeId, TEXT_ID)

    private fun findTermText(amNode: AmNode, language: String, archetypeNodeId: String?, id: String): String? {
        return if (archetypeNodeId == null) {
            null
        } else {
            val termDefinitions = amNode.termDefinitions
            when {
                termDefinitions.containsKey(language) -> findTerm(termDefinitions[language] ?: emptyList(), archetypeNodeId, id)
                language == amNode.templateLanguage -> findTerm(amNode.terms, archetypeNodeId, id)
                else -> null
            }
        }
    }

    /**
     * Finds and returns [Map] of [TermBindingItem] for node ID.
     *
     * @param amNode [AmNode]
     * @param nodeId Node ID
     * @return [Map] of [TermBindingItem]
     */
    @JvmStatic
    fun findTermBindings(amNode: AmNode, nodeId: String?): Map<String, TermBindingItem> =
        if (nodeId == null)
            mapOf()
        else
            amNode.termBindings.let {
                val map: LinkedHashMap<String, TermBindingItem> = linkedMapOf()
                it.forEach { (key, value) -> findTermBindings(nodeId, value)?.also { term -> map[key] = term } }
                map
            }

    private fun findTermBindings(nodeId: String?, bindings: Collection<TermBindingItem>) =
        if (nodeId == null) null else bindings.firstOrNull { nodeId == it.code }

    /**
     * Finds and returns [CPrimitive] for path segments.
     *
     * @param amNode [AmNode]
     * @param clazz [Class] of [CPrimitive] instance
     * @param pathSegments Path segments
     * @return [CPrimitive] for path segments
     */
    @JvmStatic
    fun <T : CPrimitive> getPrimitiveItem(amNode: AmNode, clazz: Class<T>, vararg pathSegments: String): T? =
        getAmNodes(amNode, *pathSegments)
            .firstOrNull { it.cObject is CPrimitiveObject && it.cObject.item != null && clazz.isInstance(it.cObject.item) }
            ?.let { clazz.cast((it.cObject as CPrimitiveObject).item) }

    inline fun <reified T : CPrimitive> getPrimitiveItem(amNode: AmNode, vararg pathSegments: String): T? =
        getPrimitiveItem(amNode, T::class.java, *pathSegments)

    /**
     * Finds and returns [CObject] item for path segments.
     *
     * @param amNode [AmNode]
     * @param clazz [Class] of [CObject] instance
     * @param pathSegments Path segments
     * @return [CObject] item for path segments
     */
    @JvmStatic
    fun <T : CObject> getCObjectItem(amNode: AmNode, clazz: Class<T>, vararg pathSegments: String): T? =
        getAmNodes(amNode, *pathSegments).firstOrNull { clazz.isInstance(it.cObject) }?.let { clazz.cast(it.cObject) }

    inline fun <reified T : CObject> getCObjectItem(amNode: AmNode, vararg pathSegments: String): T? = getCObjectItem(amNode, T::class.java, *pathSegments)

    /**
     * Finds and returns [List] of [CObject] items for path segments.
     *
     * @param amNode [AmNode]
     * @param clazz [Class] of [CObject] instance
     * @param pathSegments Path segments
     * @return [List] of [CObject] items for path segments
     */
    @JvmStatic
    fun <T : CObject> getCObjectItems(amNode: AmNode, clazz: Class<T>, vararg pathSegments: String): List<T> =
        getAmNodes(amNode, *pathSegments).asSequence().filter { clazz.isInstance(it.cObject) }.map { clazz.cast(it.cObject) }.toList()

    inline fun <reified T : CObject> getCObjectItems(amNode: AmNode, vararg pathSegments: String): List<T> =
        getCObjectItems(amNode, T::class.java, *pathSegments)

    /**
     * Returns [IntervalOfInteger] lower bound.
     *
     * @param interval [IntervalOfInteger]
     * @return [IntervalOfInteger] lower bound, otherwise null
     */
    @JvmStatic
    fun getMin(interval: IntervalOfInteger?): Int? =
        if (interval != null && !interval.lowerUnbounded) {
            if (false == interval.lowerIncluded) interval.lower?.plus(1) else interval.lower
        } else {
            null
        }

    /**
     * Returns [IntervalOfInteger] uppser bound.
     *
     * @param interval [IntervalOfInteger]
     * @return [IntervalOfInteger] uppser bound, otherwise null
     */
    @JvmStatic
    fun getMax(interval: IntervalOfInteger?): Int? =
        if (interval != null && !interval.upperUnbounded) {
            if (false == interval.upperIncluded) interval.upper?.plus(1) else interval.upper
        } else {
            null
        }

    /**
     * Returns [AmNode] parent attribute name of the first attribute that contains [AmNode] child.
     *
     * @param parent [AmNode]
     * @param child [AmNode]
     * @returns [AmNode] parent attribute name of the first attribute that contains [AmNode] child
     */
    @JvmStatic
    fun attributeNameOf(parent: AmNode, child: AmNode): String? = parent.attributes.entries.firstOrNull { it.value.children.contains(child) }?.key

    /**
     * Returns [AmNode] parent attribute of the first attribute that contains [AmNode] child.
     *
     * @param parent [AmNode]
     * @param child [AmNode]
     * @returns [AmNode] parent attribute of the first attribute that contains [AmNode] child
     */
    @JvmStatic
    fun attributeOf(parent: AmNode, child: AmNode): AmAttribute? = parent.attributes.entries.firstOrNull { it.value.children.contains(child) }?.value

    /**
     * Returns [AmNode] [AmAttribute] names for the range (lower and upper range are inclusive).
     *
     * @param amNode [AmNode]
     * @param from Range lower bound
     * @param to Range upper bound
     * @return [List] of [AmNode] [AmAttribute] names for the range
     */
    @JvmStatic
    fun getAttributeNames(amNode: AmNode, from: Int, to: Int): List<String?> =
        getInRange(amNode, from, to) { parent, child -> attributeNameOf(parent, child) }


    /**
     * Returns [AmNode] parents for the range (lower and upper range are inclusive).
     *
     * @param amNode [AmNode]
     * @param from Range lower bound
     * @param to Range upper bound
     * @return [List] of [AmNode] parents for the range
     */
    @JvmStatic
    fun getParents(amNode: AmNode, from: Int, to: Int): List<AmNode> =
        getInRange(amNode, from, to) { parent, _ -> parent }

    private fun <T> getInRange(amNode: AmNode, from: Int, to: Int, function: (AmNode, AmNode) -> T): List<T> {
        val list = mutableListOf<T>()
        var node: AmNode? = amNode
        var index = 0
        while (node != null && index <= to) {
            val parent: AmNode? = node.parent
            if (index >= from && parent != null) {
                list.add(function.invoke(parent, node))
            }
            node = parent
            index++
        }
        return list.toList()
    }

    /**
     * Return [List] of optionally [AmAttribute].
     *
     * @param [amNode]
     * @return [List] of optionally [AmAttribute]
     */
    @JvmStatic
    fun getOptOnlyAttributes(amNode: AmNode): List<AmAttribute> = amNode.attributes.values.filter { !it.rmOnly }

    /**
     * Checks if [AmAttribute] name is constrained (not optional).
     *
     * @param amNode [AmNode]
     * @return [Boolean] indicating if [AmAttribute] name is constrained
     */
    @JvmStatic
    fun isNameConstrained(amNode: AmNode): Boolean = amNode.attributes[NAME_ATTRIBUTE].let { it != null && !it.rmOnly }

    /**
     * Checks if [DvText] (for name attribute) matches with [AmNode].
     *
     * @param amNode [AmNode]
     * @param name [DvText]
     * @return [Boolean] indicating if  [DvText] (for name attribute) matches with [AmNode]
     */
    @JvmStatic
    fun nameMatches(amNode: AmNode, name: DvText): Boolean = !isNameConstrained(amNode) || constrainedNameMatches(amNode, name)

    /**
     * Returns [CCodePhrase] for the name [AmAttribute].
     *
     * @param amNode [AmNode]
     * @return [CCodePhrase] if found, otherwise, null
     */
    @JvmStatic
    fun getNameCodePhrase(amNode: AmNode): CCodePhrase? =
        getAmNode(amNode, NAME_ATTRIBUTE, CODE_PHRASE_ATTRIBUTE)?.let { if (it.cObject is CCodePhrase) it.cObject else null }


    private fun nameMatches(am: AmNode, locatable: Locatable): Boolean = nameMatches(am, locatable.name ?: throw AmException("Locatable name is required."))

    private fun nameMatches(amNode: AmNode, name: String): Boolean {
        if (name != amNode.name) {
            val nameCodePhrase = getNameCodePhrase(amNode)
            return if (nameCodePhrase == null) {
                false
            } else {
                val codeList: List<String> = nameCodePhrase.codeList
                codeList.isEmpty() || codeList.stream().anyMatch { name == findTermText(amNode, it) }
            }
        }
        return true
    }

    private fun constrainedNameMatches(amNode: AmNode, name: DvText): Boolean {
        val nameCodePhrase = getNameCodePhrase(amNode)
        if (nameCodePhrase == null) {
            val rmName = name.value
            val amName: String? = amNode.name
            return if (rmName != amName && amName != null && rmName != null) NAME_SUFFIX.matcher(rmName).replaceAll("") == amName else true
        } else {
            if (name is DvCodedText) {
                val nameCodePhraseTerminologyId = nameCodePhrase.terminologyId
                if (nameCodePhraseTerminologyId != null && !nameCodePhrase.terminologyId?.value.isNullOrBlank()) {
                    val terminologyId = name.definingCode?.terminologyId?.value
                    if (!Objects.equals(terminologyId, nameCodePhraseTerminologyId.value)) {
                        return false
                    }
                }
                return nameCodePhrase.codeList.isEmpty() || nameCodePhrase.codeList.contains(name.definingCode?.codeString)
            }
            return false
        }
    }

    /**
     * Checks if [Locatable] archetype matches with [AmNode].
     *
     * @param amNode [AmNode]
     * @param locatable [Locatable]
     * @return [Boolean] indicating if [Locatable] archetype matches with [AmNode]
     */
    @JvmStatic
    fun archetypeMatches(amNode: AmNode, locatable: Locatable): Boolean {
        if (amNode.archetypeNodeId != null && amNode.archetypeNodeId != amNode.nodeId) {
            if (amNode.archetypeNodeId != locatable.archetypeNodeId) {
                val archetypeDetails = locatable.archetypeDetails
                return archetypeDetails?.archetypeId != null && amNode.archetypeNodeId == archetypeDetails.archetypeId?.value
            }
        } else {
            return amNode.nodeId == null || locatable.archetypeNodeId != null && amNode.nodeId == locatable.archetypeNodeId
        }
        return true
    }

    /**
     * Checks if [Locatable] archetype and name matches with [AmNode].
     *
     * @param amNode [AmNode]
     * @param locatable [Locatable]
     * @return [Boolean] indicating if [Locatable] archetype and name matches with [AmNode]
     */
    @JvmStatic
    fun matches(amNode: AmNode, locatable: Locatable): Boolean = archetypeMatches(amNode, locatable) && nameMatches(amNode, locatable)

    /**
     * Finds and returns [AmNode] for the AQL path.
     *
     * @param amNode [AmNode]
     * @param path AQL path
     * @return [AmNode] if found, otherwise null
     */
    @JvmStatic
    fun resolvePath(amNode: AmNode, path: String?): AmNode? =
        if (path.isNullOrBlank())
            amNode
        else
            resolvePathRecursively(amNode, PathUtils.getPathSegments(path), 0)

    private fun resolvePathRecursively(amNode: AmNode, pathSegments: List<PathSegment>, index: Int): AmNode? {
        if (index > pathSegments.size - 1) {
            return amNode
        }
        val pathSegment = pathSegments[index]

        val amAttribute = amNode.attributes[pathSegment.element] ?: return null

        val name = pathSegment.name
        val archetypeNodeId = pathSegment.archetypeNodeId

        val constrainedAmNode = name?.let {
            amAttribute.children
                .firstOrNull { child -> isNameConstrained(child) && segmentMatches(child, archetypeNodeId, NAME_SUFFIX.matcher(name).replaceAll("")) }
        }
        return if (constrainedAmNode == null) {
            val child = amAttribute.children.firstOrNull { segmentMatches(it, archetypeNodeId, name) }
            if (child == null)
                null
            else
                resolvePathRecursively(child, pathSegments, index + 1)
        } else {
            resolvePathRecursively(constrainedAmNode, pathSegments, index + 1)
        }
    }

    /**
     * Checks if path segment archetype node ID and name matches with [AmNode].
     *
     * @param amNode [AmNode]
     * @param segmentArchetypeNodeId Path segment archetype node ID
     * @param segmentName Path segment name
     * @return [Boolean] indicating if path segment archetype node ID and name matches with [AmNode]
     */
    @JvmStatic
    fun segmentMatches(amNode: AmNode, segmentArchetypeNodeId: String?, segmentName: String?): Boolean =
        (segmentArchetypeNodeId == null || segmentArchetypeNodeId == amNode.archetypeNodeId) &&
                (segmentName == null || !isNameConstrained(amNode) || nameMatches(amNode, NAME_SUFFIX.matcher(segmentName).replaceAll("")))

    /**
     * Finds and returns [AmNode] that matches with [Locatable] archetype and name.
     *
     * @param amNodes [Iterable] of [AmNode]
     * @param locatable [Locatable]
     * @return [AmNode] if found, otherwise null
     */
    @JvmStatic
    fun findMatchingNode(amNodes: Iterable<AmNode>, locatable: Locatable): AmNode? {
        var matchingNode: AmNode? = null
        for (amNode in amNodes) {
            if (archetypeMatches(amNode, locatable)) {
                if (nameMatches(amNode, locatable)) {
                    if (locatable.name!!.value == amNode.name)
                        return amNode
                    else
                        matchingNode = amNode
                } else if (matchingNode == null) {
                    matchingNode = amNode
                }
            }
        }
        return matchingNode
    }
}
