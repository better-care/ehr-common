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

package org.openehr.rm.composition

import care.better.platform.annotation.Open
import jakarta.xml.bind.annotation.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.openehr.base.basetypes.ObjectRef
import org.openehr.rm.datastructures.ItemStructure

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "CARE_ENTRY", propOrder = [
        "protocol",
        "guidelineId"]
)
@XmlSeeAlso(value = [Evaluation::class, Observation::class, Instruction::class, Action::class])
@Serializable
@SerialName("CARE_ENTRY")
@Open
abstract class CareEntry : Entry() {
    companion object {
        @Suppress("unused")
        private const val serialVersionUID: Long = 0L
    }

    var protocol: ItemStructure? = null

    @XmlElement(name = "guideline_id")
    @SerialName("guideline_id")
    var guidelineId: ObjectRef? = null

    override fun visitProperties(ctx: care.better.platform.visitor.RmVisitorContext) {
        super.visitProperties(ctx)
        protocol?.visit("protocol", ctx)
        guidelineId?.visit("guideline_id", ctx)
    }
}
