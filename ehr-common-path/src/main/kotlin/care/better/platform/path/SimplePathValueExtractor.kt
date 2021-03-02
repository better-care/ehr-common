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

package care.better.platform.path

import org.apache.commons.lang3.StringUtils
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

/**
 * @author Bostjan Lah
 * @author Primoz Delopst
 * @since 3.1.0
 *
 * Implementation of [PathValueExtractor] that extract values for a path from a given object.
 *
 * @constructor Creates a new instance of [SimplePathValueExtractor]
 * @param path Path [String]
 */
open class SimplePathValueExtractor(path: String?) : PathValueExtractor {
    private var pathSegmentsWithNames: List<Pair<PathSegment, String>> = PathUtils.getPathSegments(path).map { Pair(it, PathUtils.getPropertyName(it.element)) }
    private val propertyMethods: ConcurrentMap<MethodKey, (Any) -> Any?> = ConcurrentHashMap()


    /**
     * Extracts and returns [List] of values using the path.
     *
     * @param node Root node
     * @return [List] of extracted values
     */
    override fun getValue(node: Any?): List<Any> {
        return getValue(node, false)
    }

    /**
     * Extracts and returns [List] of values using the path.
     *
     * @param node Root node
     * @param ignoreExceptions [Boolean] indicating if the exception will be thrown
     * @return [List] of extracted values
     */
    open fun getValue(node: Any?, ignoreExceptions: Boolean): List<Any> =
        if (node == null || !rootMatches(node)) {
            listOf()
        } else if (pathSegmentsWithNames.isEmpty()) {
            listOf(node)
        } else {
            getValuesRecursively(listOf(node), 0, ignoreExceptions)
        }

    /**
     * Extract and returns [List] of values using the path.
     *
     * @param nodes Singleton [List] of root node when depth is null, otherwise [List] of nodes for previous [PathSegment]
     * @param index Index of the current path segment
     * @param ignoreExceptions [Boolean] indicating if the exception will be thrown
     * @return [List] of extracted values
     */
    private fun getValuesRecursively(nodes: List<Any>, index: Int, ignoreExceptions: Boolean): List<Any> {
        if (nodes.isEmpty()) {
            return emptyList()
        }
        val (pathSegment, propertyName) = pathSegmentsWithNames[index]
        val nodesForSegment = getNodesForSegment(nodes, pathSegment, propertyName, ignoreExceptions)

        if (index == pathSegmentsWithNames.size - 1) {
            return nodesForSegment
        }

        return getValuesRecursively(nodesForSegment, index + 1, ignoreExceptions)
    }

    /**
     * Returns [List] of nodes for the [PathSegment] from the [List] of nodes for the previous [PathSegment].
     *
     * @param previousSegmentNodes [List] of nodes for the previous [PathSegment]
     * @param pathSegment [PathSegment]
     * @param propertyName Name of the [Class] property
     * @param ignoreExceptions [Boolean] indicating if the exception will be thrown
     * @return [List] of nodes for the [PathSegment]
     */
    private fun getNodesForSegment(previousSegmentNodes: List<Any>, pathSegment: PathSegment, propertyName: String, ignoreExceptions: Boolean): List<Any> =
        with(mutableListOf<Any>()) {
            previousSegmentNodes.forEach { nodeForPreviousSegment ->
                asList(getNodeForProperty(propertyName, nodeForPreviousSegment, ignoreExceptions)).forEach { nodeForProperty ->
                    if (nodeForProperty != null && elementMatches(nodeForProperty, pathSegment)) {
                        this.add(nodeForProperty)
                    }
                }
            }
            this.toList()
        }

    /**
     * Returns node or [List] of nodes for [Class] property.
     *
     * @param propertyName Name of the [Class] property
     * @param node Object from which value of the [Class] property will be retrieved
     * @param ignoreExceptions [Boolean] indicating if the exception will be thrown
     * @return Node or [List] of nodes for the [Class] property
     */
    private fun getNodeForProperty(propertyName: String, node: Any, ignoreExceptions: Boolean): Any? =
        propertyMethods.computeIfAbsent(MethodKey(node.javaClass, propertyName)) { getMethod(it, ignoreExceptions) }.invoke(node)

    /**
     * Returns function that will invokes [Method] on the object and returns the invocation result.
     *
     * @param methodKey [MethodKey]
     * @param ignoreExceptions [Boolean] indicating if the exception will be thrown
     * @return  function that will retrieve call method on the invoked object and return the result
     */
    private fun getMethod(methodKey: MethodKey, ignoreExceptions: Boolean): (Any) -> Any? {
        val methodName: String = StringUtils.capitalize(methodKey.propertyName)
        try {
            val method = methodKey.clazz.getMethod("get$methodName")
            return { invoke(method, it) }
        } catch (ignored: NoSuchMethodException) {
        } catch (ignored: SecurityException) {
        }

        try {
            val method = methodKey.clazz.getMethod("is$methodName")
            return { invoke(method, it) }
        } catch (ignored: NoSuchMethodException) {
        } catch (ignored: SecurityException) {
        }

        try {
            val method = methodKey.clazz.getMethod(methodName)
            return { invoke(method, it) }
        } catch (e: NoSuchMethodException) {
            return if (ignoreExceptions) { _ -> null } else throw PathValueExtractorException(e)
        }
    }

    /**
     * Invokes [Method] on the object.
     *
     * @param method [Method]
     * @param node Object on which [Method] will be invoked
     * @return Invocation result
     */
    private operator fun invoke(method: Method, node: Any): Any? =
        try {
            method.invoke(node)
        } catch (_: IllegalAccessException) {
            null
        } catch (_: InvocationTargetException) {
            null
        }

    /**
     * Checks if the [PathSegment] matches with the object.
     *
     * @param node Object
     * @param pathSegment [PathSegment]
     * @return [Boolean] indicating if [PathSegment] matches with the objects.
     */
    protected open fun elementMatches(node: Any, pathSegment: PathSegment): Boolean = true

    /**
     * Checks if the rood objects matches with the [List] of [PathSegment].
     * @param node Root object
     */
    protected open fun rootMatches(node: Any?): Boolean = node != null

    /**
     * Returns [List] of [PathSegment].
     *
     * @return [List] of [PathSegment]
     */
    fun getPathSegments(): List<PathSegment> = pathSegmentsWithNames.map { it.first }

    /**
     * Transforms objects to the [List] of objects.
     *
     * @param node Object or a [List] of objects
     * @return [List] of objects
     */
    @Suppress("UNCHECKED_CAST")
    private fun asList(node: Any?): List<Any?> =
        if (node is List<*>) {
            node
        } else {
            if (node == null) emptyList() else listOf(node)
        }

    /**
     * Holds information about method key.
     *
     * @constructor Creates a new instance of [MethodKey]
     * @param clazz [Class]
     * @param propertyName Name of the property
     */
    private data class MethodKey(val clazz: Class<*>, val propertyName: String) {
        override fun equals(other: Any?): Boolean =
            when {
                this === other -> true
                other == null || javaClass != other.javaClass -> false
                else -> clazz == (other as MethodKey).clazz && propertyName == other.propertyName
            }

        override fun hashCode(): Int = Objects.hash(clazz, propertyName)
    }

    override fun toString(): String =
        "${javaClass.simpleName}[${pathSegmentsWithNames.joinToString("/") { it.first.asPathSegment() }}]"
}
