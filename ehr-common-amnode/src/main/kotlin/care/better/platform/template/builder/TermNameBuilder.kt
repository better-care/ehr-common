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

package care.better.platform.template.builder

import care.better.platform.template.AmNode
import care.better.platform.template.AmUtils

/**
 * @author Bostjan Lah
 * @author Primoz Delopst
 * @since 3.1.0
 */
open class TermNameBuilder : NameBuilder {
    override fun getName(amNode: AmNode): String? {
        val nodeId = amNode.nodeId ?: amNode.cObject?.nodeId
        return if (nodeId == null) null else AmUtils.findTerm(amNode.terms, nodeId, "text")
    }
}
