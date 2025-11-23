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
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlType

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CLUSTER", propOrder = ["items"])
@Serializable
@SerialName("CLUSTER")
@Open
class Cluster : Item() {
    companion object {
        private const val serialVersionUID: Long = 0L
    }

    @XmlElement(required = true)
    @Required
    var items: MutableList<Item> = mutableListOf()

    override fun visit(attributeName: String, ctx: RmVisitorContext) {
        if (ctx.beforeLocatable(attributeName, this, "CLUSTER")) {
            // Visit parent properties (from Locatable via Item)
            name?.visit("name", ctx)
            uid?.visit("uid", ctx)
            links.forEach { it.visit("links", ctx) }
            archetypeDetails?.visit("archetype_details", ctx)
            feederAudit?.visit("feeder_audit", ctx)
            archetypeNodeId?.let { ctx.visitValue("archetype_node_id", it) }

            // Visit own properties
            items.forEach { it.visit("items", ctx) }

            ctx.afterLocatable(attributeName, this, "CLUSTER")
        }
    }
}
