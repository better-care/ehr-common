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
 * @author Primoz Delopst
 * @since 3.1.0
 */
class NameAndNodeMatchingPathValueExtractor(path: String) : SimplePathValueExtractor(path) {
    override fun elementMatches(element: Any?, archetypeId: String?, segmentNumber: Int): Boolean =
        if (element is Locatable) {
            if (archetypeId == element.archetypeNodeId) {
                val segment = getPathSegments()[segmentNumber]
                if (segment.name != null) {
                    when {
                        segment.prefix == null -> element.name?.value == segment.name
                        "uid/value".equals(segment.prefix, ignoreCase = true) -> element.uid?.value == segment.name
                        else -> false
                    }
                } else {
                    true
                }
            } else {
                false
            }
        } else {
            super.elementMatches(element, archetypeId, segmentNumber)
        }
}
