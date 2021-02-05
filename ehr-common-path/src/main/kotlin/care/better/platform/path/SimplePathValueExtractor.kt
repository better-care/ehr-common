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
 * @author Primoz Delopst
 * @since 3.1.0
 */
open class SimplePathValueExtractor(path: String) : PathValueExtractor{
    private var pathSegments: List<PathSegment> = PathUtils.getPathSegments(path)
    private val propertyNames: List<String> = pathSegments.map { PathUtils.getPropertyName(it.element) }
    private val propertyMethods: ConcurrentMap<MethodKey, (Any) -> Any?> = ConcurrentHashMap()


    override fun getValue(node: Any?): List<Any?> {
        return getValue(node, false)
    }

    fun getValue(rootNode: Any?, quiet: Boolean): List<Any?> {
        if (!rootMatches(rootNode)) {
            return listOf()
        }

        var values: List<Any?> = listOf(rootNode)
        var segmentIndex = 0
        for (pathSegment in pathSegments) {
            values = getValuesForSegment(quiet, values, segmentIndex++, pathSegment.archetypeNodeId)
            if (values.isEmpty()) {
                break
            }
        }
        return values
    }

    private fun getValuesForSegment(quiet: Boolean, currentValues: List<Any?>, segmentIndex: Int, archetypeNodeId: String?): List<Any?> =
        with(mutableListOf<Any?>()){
            val propertyName = propertyNames[segmentIndex]
            currentValues.forEach {
                if (it != null) {
                    addMatchingValues(this, asList(getPropertyValue(propertyName, it, quiet)), segmentIndex, archetypeNodeId)
                }
            }
            this.toList()
        }


    private fun addMatchingValues(newValues: MutableList<Any?>, propertyValues: List<Any?>, segmentIndex: Int, archetypeNodeId: String?) {
        propertyValues.forEach {
            if (it != null && elementMatches(it, archetypeNodeId, segmentIndex)) {
                newValues.add(it)
            }
        }
    }

    private fun getPropertyValue(propertyName: String, value: Any, quiet: Boolean): Any? =
        propertyMethods.computeIfAbsent(MethodKey(value.javaClass, propertyName)) { getMethod(it, quiet) }.invoke(value)

    private fun getMethod(methodKey: MethodKey, quiet: Boolean): (Any) -> Any? {
        val methodName: String = StringUtils.capitalize(methodKey.propertyName)
        try {
            return { invoke(methodKey.clazz.getMethod("get$methodName"), it, quiet) }
        } catch (ignored: NoSuchMethodException) {
        } catch (ignored: SecurityException) {
        }

        try {
            return { invoke(methodKey.clazz.getMethod("is$methodName"), it, quiet) }
        } catch (ignored: NoSuchMethodException) {
        } catch (ignored: SecurityException) {
        }

        return try {
            { invoke(methodKey.clazz.getMethod(methodName), it, quiet) }
        } catch (e: NoSuchMethodException) {
            return if (quiet) { _ -> null } else throw PathValueExtractorException(e)
        }
    }

    private operator fun invoke(method: Method, value: Any, quiet: Boolean): Any? =
        try {
            method.invoke(value)
        } catch (_: IllegalAccessException) {
            null
        } catch (_: InvocationTargetException) {
            null
        }

    protected open fun elementMatches(element: Any?, archetypeId: String?, segmentNumber: Int): Boolean = true

    protected open fun rootMatches(root: Any?): Boolean = root != null

    fun getPathSegments(): List<PathSegment> = pathSegments

    @Suppress("UNCHECKED_CAST")
    private fun asList(value: Any?): List<Any> =
        if (value is List<*>) {
            value as List<Any>
        } else {
            if (value == null) emptyList() else listOf(value)
        }

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
        "${javaClass.simpleName}[${pathSegments.joinToString("/") { it.asPathSegment() }}]"
}
