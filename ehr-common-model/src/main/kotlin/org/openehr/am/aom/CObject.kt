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

package org.openehr.am.aom

import care.better.platform.annotation.Required
import org.openehr.base.foundationtypes.IntervalOfInteger
import javax.xml.bind.annotation.*

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "C_OBJECT", propOrder = [
        "rmTypeName",
        "occurrences",
        "nodeId",
        "siblingOrder"])
@XmlSeeAlso(value = [ArchetypeSlot::class, ConstraintRef::class, ArchetypeInternalRef::class, CDefinedObject::class])
abstract class CObject : ArchetypeConstraint() {
    companion object {
        private const val serialVersionUID: Long = 0L
    }

    @XmlElement(name = "rm_type_name", required = true)
    @Required
    var rmTypeName: String? = null

    @XmlElement(required = true)
    @Required
    var occurrences: IntervalOfInteger? = null

    @XmlElement(name = "node_id", required = true)
    @Required
    var nodeId: String? = null

    @XmlElement(name = "sibling_order", type = SiblingOrder::class)
    var siblingOrder: SiblingOrder? = null
}
