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

package org.openehr.rm.datastructures

import care.better.platform.annotation.Open
import care.better.platform.annotation.Required
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.openehr.rm.datatypes.DvCodedText
import org.openehr.rm.datatypes.DvDuration
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
    name = "INTERVAL_EVENT", propOrder = [
        "width",
        "sampleCount",
        "mathFunction"])
@Serializable
@SerialName("INTERVAL_EVENT")
@Open
class IntervalEvent : Event() {
    companion object {
        private const val serialVersionUID: Long = 0L
    }

    @XmlElement(required = true)
    @Required
    var width: DvDuration? = null

    @XmlElement(name = "sample_count")
    @SerialName("sample_count")
    var sampleCount: Int? = null

    @XmlElement(name = "math_function", required = true)
    @Required
    @SerialName("math_function")
    var mathFunction: DvCodedText? = null

    override fun visit(attributeName: String, ctx: care.better.platform.visitor.RmVisitorContext) {
        // Visit parent properties (from Locatable via Event)
        name?.visit("name", ctx)
        uid?.visit("uid", ctx)
        links.forEach { it.visit("links", ctx) }
        archetypeDetails?.visit("archetype_details", ctx)
        feederAudit?.visit("feeder_audit", ctx)
        archetypeNodeId?.let { ctx.visitValue("archetype_node_id", it) }

        // Visit Event properties
        time?.visit("time", ctx)
        data?.visit("data", ctx)
        state?.visit("state", ctx)

        // Visit own properties
        width?.visit("width", ctx)
        sampleCount?.let { ctx.visitValue("sample_count", it) }
        mathFunction?.visit("math_function", ctx)

        ctx.visitLocatable(attributeName, this, "INTERVAL_EVENT")
    }
}
