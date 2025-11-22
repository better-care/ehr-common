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

package org.openehr.rm.ehr

import care.better.platform.annotation.OpenEhrName
import care.better.platform.annotation.Required
import care.better.platform.visitor.RmVisitorContext
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.openehr.rm.common.Locatable
import org.openehr.rm.common.PartySelf
import org.openehr.rm.datastructures.ItemStructure
import javax.xml.bind.annotation.*

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EHR_STATUS", namespace = "http://schemas.openehr.org/v1", propOrder = ["subject", "queryable", "modifiable", "otherDetails"])
@XmlRootElement(namespace = "http://schemas.openehr.org/v1")
@Serializable
@SerialName("EHR_STATUS")
class EhrStatus : Locatable() {
    companion object {
        private const val serialVersionUID: Long = 0L
    }

    @XmlElement(required = true)
    @Required
    var subject: PartySelf? = null

    @XmlElement(name = "is_queryable")
    @OpenEhrName("is_queryable")
    @SerialName("is_queryable")
    var queryable: Boolean = true

    @XmlElement(name = "is_modifiable")
    @OpenEhrName("is_modifiable")
    @SerialName("is_modifiable")
    var modifiable: Boolean = true

    @XmlElement(name = "other_details")
    @SerialName("other_details")
    var otherDetails: ItemStructure? = null

    fun visit(attributeName: String, ctx: RmVisitorContext) {
        ctx.beforeLocatable(attributeName, this, "EHR_STATUS")
        // Visit parent properties
        name?.visit("name", ctx)
        uid?.visit("uid", ctx)
        links.forEach { it.visit("links", ctx) }
        archetypeDetails?.visit("archetype_details", ctx)
        feederAudit?.visit("feeder_audit", ctx)
        archetypeNodeId?.let { ctx.visitValue("archetype_node_id", it) }

        // Visit own properties
        subject?.visit("subject", ctx)
        ctx.visitValue("is_queryable", queryable)
        ctx.visitValue("is_modifiable", modifiable)
        otherDetails?.visit("other_details", ctx)

        ctx.afterLocatable(attributeName, this, "EHR_STATUS")
    }
}
