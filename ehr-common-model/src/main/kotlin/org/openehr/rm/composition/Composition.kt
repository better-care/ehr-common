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
import org.openehr.rm.common.Locatable
import org.openehr.rm.common.PartyProxy
import org.openehr.rm.datatypes.CodePhrase
import org.openehr.rm.datatypes.DvCodedText
import javax.xml.bind.annotation.*

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "COMPOSITION", propOrder = [
        "language",
        "territory",
        "category",
        "composer",
        "context",
        "content"])
@XmlRootElement
@Serializable
@SerialName("COMPOSITION")
@Open
class Composition : Locatable() {
    companion object {
        private const val serialVersionUID: Long = 0L
    }

    @XmlElement(required = true)
    @Required
    var language: CodePhrase? = null

    @XmlElement(required = true)
    @Required
    var territory: CodePhrase? = null

    @XmlElement(required = true)
    @Required
    var category: DvCodedText? = null

    @XmlElement(required = true)
    @Required
    var composer: PartyProxy? = null

    var context: EventContext? = null

    var content: MutableList<ContentItem> = mutableListOf()

    fun visit(attributeName: String, ctx: RmVisitorContext) {
        ctx.beforeLocatable(attributeName, this, "COMPOSITION")
        // Visit parent properties
        name?.visit("name", ctx)
        uid?.visit("uid", ctx)
        links.forEach { it.visit("links", ctx) }
        archetypeDetails?.visit("archetype_details", ctx)
        feederAudit?.visit("feeder_audit", ctx)
        archetypeNodeId?.let { ctx.visitValue("archetype_node_id", it) }

        // Visit own properties
        language?.visit("language", ctx)
        territory?.visit("territory", ctx)
        category?.visit("category", ctx)
        composer?.visit("composer", ctx)
        context?.visit("context", ctx)
        content.forEach { it.visit("content", ctx) }
        ctx.afterLocatable(attributeName, this, "COMPOSITION")
    }
}
