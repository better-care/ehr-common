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
import care.better.platform.visitor.RmVisitorContext
import jakarta.xml.bind.annotation.XmlAccessType
import jakarta.xml.bind.annotation.XmlAccessorType
import jakarta.xml.bind.annotation.XmlElement
import jakarta.xml.bind.annotation.XmlType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.openehr.rm.common.Locatable
import org.openehr.rm.datatypes.DvDateTime
import org.openehr.rm.datatypes.DvDuration

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "HISTORY", propOrder = [
        "origin",
        "period",
        "duration",
        "events",
        "summary"]
)
@Serializable
@SerialName("HISTORY")
@Open
class History : Locatable() {
    companion object {
        @Suppress("unused")
        private const val serialVersionUID: Long = 0L
    }

    @XmlElement(required = true)
    @Required
    var origin: DvDateTime? = null

    var period: DvDuration? = null

    var duration: DvDuration? = null

    var events: MutableList<Event> = mutableListOf()

    var summary: ItemStructure? = null

    override fun visit(attributeName: String, ctx: RmVisitorContext) {
        ctx.withLocatable(attributeName, this, "HISTORY") {
            visitProperties(ctx)
        }
    }

    override fun visitProperties(ctx: RmVisitorContext) {
        super.visitProperties(ctx)
        origin?.visit("origin", ctx)
        period?.visit("period", ctx)
        duration?.visit("duration", ctx)
        ctx.withCollection("events", events, this) {
            events.forEach { it.visit("events", ctx) }
        }
        summary?.visit("summary", ctx)
    }
}
