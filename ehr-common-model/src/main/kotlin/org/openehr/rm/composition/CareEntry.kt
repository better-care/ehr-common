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
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.openehr.base.basetypes.ObjectRef
import org.openehr.rm.datastructures.ItemStructure
import javax.xml.bind.annotation.*

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "CARE_ENTRY", propOrder = [
        "protocol",
        "guidelineId"])
@XmlSeeAlso(value = [Evaluation::class, Observation::class, Instruction::class, Action::class])
@Serializable
@SerialName("CARE_ENTRY")
@Open
abstract class CareEntry : Entry() {
    companion object {
        private const val serialVersionUID: Long = 0L
    }

    var protocol: ItemStructure? = null

    @XmlElement(name = "guideline_id")
    @SerialName("guideline_id")
    var guidelineId: ObjectRef? = null

    override fun visit(attributeName: String, ctx: care.better.platform.visitor.RmVisitorContext) {
        // Visit parent properties (from Locatable via ContentItem via Entry)
        name?.visit("name", ctx)
        uid?.visit("uid", ctx)
        links.forEach { it.visit("links", ctx) }
        archetypeDetails?.visit("archetype_details", ctx)
        feederAudit?.visit("feeder_audit", ctx)
        archetypeNodeId?.let { ctx.visitValue("archetype_node_id", it) }

        // Visit Entry properties
        language?.visit("language", ctx)
        encoding?.visit("encoding", ctx)
        subject?.visit("subject", ctx)
        provider?.visit("provider", ctx)
        otherParticipations.forEach { it.visit("other_participations", ctx) }
        workFlowId?.visit("work_flow_id", ctx)

        // Visit own properties
        protocol?.visit("protocol", ctx)
        guidelineId?.visit("guideline_id", ctx)

        ctx.visitLocatable(attributeName, this, "CARE_ENTRY")
    }
}
