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

package care.better.platform.template

import org.openehr.am.aom.Cardinality
import org.openehr.base.foundationtypes.IntervalOfInteger

/**
 * @author Bostjan Lah
 * @author Primoz Delopst
 * @since 3.1.0
 */
class AmAttribute(val existence: IntervalOfInteger?, children: List<AmNode>) {
    private val _children = children.toMutableList()
    val children: List<AmNode>
        get() = _children.toList()

    var cardinality: Cardinality? = null

    /**
     * Indicates that attribute is not constrained in the template (is just part of RM)
     */
    var rmOnly = false

    fun postProcessReference(reference: AmNode, amNode: AmNode) {
        val i = children.indexOf(reference)
        _children[i] = amNode
    }
}
