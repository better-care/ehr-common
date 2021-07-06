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

import org.openehr.rm.common.Locatable

/**
 * @author Bostjan Lah
 * @author Primoz Delopst
 * @since 3.1.0
 *
 * Instance of [SimplePathValueExtractor] that extract values for a path from a given object.
 * Note that this extractor is comparing archetype id and name for [Locatable] objects.
 *
 * @constructor Creates a new instance of [NameAndNodeMatchingPathValueExtractor]
 * @param path Path [String]
 * @param matchNumberedNodes [Boolean] indicating if numbered nodes will be matched
 */
class NameAndNodeMatchingPathValueExtractor @JvmOverloads constructor(path: String?, private val matchNumberedNodes: Boolean = false) : SimplePathValueExtractor(path) {


    /**
     * Checks if the [PathSegment] matches with the object.
     * Note that archetype id and name are compared only for [Locatable] objects.
     *
     * @param node Object
     * @param pathSegment [PathSegment]
     * @return [Boolean] indicating if [PathSegment] matches with the objects.
     */
    override fun elementMatches(node: Any, pathSegment: PathSegment): Boolean {

        if (node !is Locatable) {
            return super.elementMatches(node, pathSegment)
        }

        if (pathSegment.archetypeNodeId != node.archetypeNodeId) {
            return false
        }

        if (pathSegment.name == null) {
            return true
        }

        val regex = Regex("${Regex.escape(pathSegment.name.orEmpty())}${if (matchNumberedNodes) "(\\s#\\d+)?" else ""}")
        return when {
            pathSegment.prefix == null -> regex.matches(node.name?.value.orEmpty())
            "uid/value".equals(pathSegment.prefix, ignoreCase = true) -> regex.matches(node.uid?.value.orEmpty())
            else -> false
        }
    }
}
