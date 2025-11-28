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
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.openehr.rm.common.Locatable
import org.openehr.rm.datatypes.DvDateTime
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
    name = "HISTORY", propOrder = [
        "origin",
        "period",
        "duration",
        "events",
        "summary"])
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
        if (ctx.beforeLocatable(attributeName, this, "HISTORY")) {
            visitProperties(ctx)
            ctx.afterLocatable(attributeName, this, "HISTORY")
        }
    }

    override fun visitProperties(ctx: RmVisitorContext) {
        super.visitProperties(ctx)
        origin?.visit("origin", ctx)
        period?.visit("period", ctx)
        duration?.visit("duration", ctx)
        if (ctx.beforeCollection("events", events, this)) {
            events.forEach { it.visit("events", ctx) }
            ctx.afterCollection("events", events, this)
        }
        summary?.visit("summary", ctx)
    }
}
