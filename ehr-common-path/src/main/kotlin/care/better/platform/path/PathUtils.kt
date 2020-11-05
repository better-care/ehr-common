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

@file:JvmName("PathUtils")

package care.better.platform.path

import org.apache.commons.lang3.StringUtils
import java.lang.reflect.Method
import java.util.*
import java.util.regex.Pattern

/**
 * @author Bostjan Lah
 * @author Primoz Delopst
 * @since 3.1.0
 *
 * Set of utilities used to transform path and path segment [String].
 */
object PathUtils {
    private val NODE_PATTERN = Pattern.compile(
        "/([a-zA-Z-.0-9_]+)" +
                "(\\[\\s*([a-zA-Z-.0-9_]+\\*?)" +
                "(((\\s*,\\s*)|(\\s+and\\s+[^\\s]+\\s*=\\s*))" +
                "((['\"])(.*?(?<!\\\\))\\9))?" +
                "\\s*\\])*", Pattern.CASE_INSENSITIVE)

    private val PREDICATE_PATTERN = Pattern.compile("\\s*and\\s+([^\\s=]+)\\s*=\\s*", Pattern.CASE_INSENSITIVE)


    /**
     * Returns [List] of [PathSegment] for the path.
     *
     * @param path Path [String]
     * @return [List] of [PathSegment]
     */
    @JvmStatic
    fun getPathSegments(path: String?): List<PathSegment> {
        if (path.isNullOrBlank()) {
            return emptyList()
        }
        val pathSegments: MutableList<PathSegment> = ArrayList<PathSegment>()

        val nodeMatcher = NODE_PATTERN.matcher("/$path")
        while (nodeMatcher.find()) {
            val segment = if (nodeMatcher.groupCount() == 1) {
                PathSegment(nodeMatcher.group(1), null)
            } else if (nodeMatcher.groupCount() >= 10) {
                val quotedName = nodeMatcher.group(10)
                if (quotedName == null) {
                    PathSegment(nodeMatcher.group(1), nodeMatcher.group(3))
                } else {
                    val name = quotedName.replace("\\'", "'").replace("\\\"", "\"").replace("\\\\", "\\")
                    val fullPrefix = nodeMatcher.group(7)
                    if (fullPrefix == null) {
                        PathSegment(nodeMatcher.group(1), nodeMatcher.group(3), name, null)
                    } else {
                        val prefix = getPrefixPath(fullPrefix)
                        PathSegment(nodeMatcher.group(1), nodeMatcher.group(3), name, if (("name/value" == prefix)) null else prefix)
                    }
                }
            } else {
                PathSegment(nodeMatcher.group(1), nodeMatcher.group(3))
            }
            pathSegments.add(segment)
        }

        return pathSegments.toList()
    }

    private fun getPrefixPath(path: String): String? =
        with(PREDICATE_PATTERN.matcher(path)) {
            if (this.matches()) this.group(1).toLowerCase() else null
        }

    /**
     * Returns path [String] for the [List] of [PathSegment].
     *
     * @param pathSegments [List] of [PathSegment]
     * @return Path [String]
     */
    @JvmStatic
    fun buildPath(pathSegments: List<PathSegment>): String =
        with(StringBuilder()) {
            pathSegments.forEach {
                this.append('/').append(it.element)
                if (it.archetypeNodeId != null) {
                    this.append('[').append(it.archetypeNodeId).append(']')
                }
            }
            if (this.isEmpty()) "" else this.substring(1)
        }

    /**
     * Underscores and returns path [String].
     *
     * @param path [String]
     * @return Underscored path [String]
     */
    @JvmStatic
    fun underscorePath(path: String): String =
        with(StringBuilder()) {
            StringUtils.splitByCharacterTypeCamelCase(path).forEach {
                if ("_" != it) {
                    this.append('_').append(it.toLowerCase())
                }
            }
            if (this.isNotEmpty()) this.substring(1) else ""
        }

    /**
     * Returns getter [Method] name for the [PathSegment] name.
     *
     * @param pathSegmentName [PathSegment] name
     * @return Getter [Method] name
     */
    @JvmStatic
    fun getGetter(pathSegmentName: String): String =
        with(StringBuilder()) {
            StringUtils.split(pathSegmentName, '_').forEach {
                this.append(it.substring(0, 1).toUpperCase()).append(it.substring(1))
            }
            this.toString()
        }

    /**
     * Returns [Class] property name for the [PathSegment] name.
     *
     * @param pathSegmentName [PathSegment] name
     * @return [Class] property name
     */
    @JvmStatic
    fun getPropertyName(pathSegmentName: String?): String =
        StringUtils.split(pathSegmentName, '_').let {
            with(StringBuilder(it[0])) {
                it.drop(1).forEach { part ->
                    this.append(Character.toUpperCase(part[0])).append(part.substring(1))
                }
                this.toString()
            }
        }

    /**
     * Matches two whole paths with predicates included.
     *
     * @param firstPath first path
     * @param secondPath second path
     *
     * @return [Boolean] indicating if paths match
     */
    @JvmStatic
    fun matchesPaths(firstPath: String, secondPath: String): Boolean {
        return matchesSegments(getPathSegments(firstPath), getPathSegments(secondPath))
    }

    /**
     * Matches two [List] of [PathSegment].
     *
     * @param firstSegments [List] of [PathSegment]
     * @param secondSegments [List] of [PathSegment]
     *
     * @return [Boolean] indicating if paths match
     */
    @JvmStatic
    fun matchesSegments(firstSegments: List<PathSegment>, secondSegments: List<PathSegment>): Boolean {
        if (firstSegments.size != secondSegments.size) {
            return false
        }
        for (i in secondSegments.indices) {
            if (!firstSegments[i].matchesSegment(secondSegments[i])) {
                return false
            }
        }
        return true
    }
}
