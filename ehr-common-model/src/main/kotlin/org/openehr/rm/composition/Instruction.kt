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
import care.better.platform.annotation.Required
import care.better.platform.visitor.RmVisitorContext
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.openehr.rm.datatypes.DvDateTime
import org.openehr.rm.datatypes.DvParsable
import org.openehr.rm.datatypes.DvText
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlType

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "INSTRUCTION", propOrder = [
        "narrative",
        "expiryTime",
        "wfDefinition",
        "activities"
    ])
@Serializable
@SerialName("INSTRUCTION")
@Open
class Instruction : CareEntry() {
    companion object {
        @Suppress("unused")
        private const val serialVersionUID: Long = 0L
    }

    @XmlElement(required = true)
    @Required
    var narrative: DvText? = null

    @XmlElement(name = "expiry_time")
    @SerialName("expiry_time")
    var expiryTime: DvDateTime? = null

    @XmlElement(name = "wf_definition")
    @SerialName("wf_definition")
    var wfDefinition: DvParsable? = null

    var activities: MutableList<Activity> = mutableListOf()

    override fun visit(attributeName: String, ctx: RmVisitorContext) {
        if (ctx.beforeLocatable(attributeName, this, "INSTRUCTION")) {
            visitProperties(ctx)
            ctx.afterLocatable(attributeName, this, "INSTRUCTION")
        }
    }

    override fun visitProperties(ctx: RmVisitorContext) {
        super.visitProperties(ctx)
        narrative?.visit("narrative", ctx)
        expiryTime?.visit("expiry_time", ctx)
        wfDefinition?.visit("wf_definition", ctx)
        activities.forEach { it.visit("activities", ctx) }
    }
}
