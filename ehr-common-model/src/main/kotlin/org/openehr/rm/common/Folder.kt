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

package org.openehr.rm.common

import care.better.platform.annotation.Open
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.openehr.base.basetypes.ObjectRef
import org.openehr.rm.datastructures.ItemStructure
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlType

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "FOLDER", propOrder = [
        "folders",
        "items",
        "details"])
@Serializable
@SerialName("FOLDER")
@Open
class Folder : Locatable() {
    companion object {
        private const val serialVersionUID: Long = 0L
    }

    var folders: MutableList<Folder> = mutableListOf()
    var items: MutableList<ObjectRef> = mutableListOf()
    var details: ItemStructure? = null

    override fun visit(attributeName: String, ctx: care.better.platform.visitor.RmVisitorContext) {
        // Visit parent properties
        name?.visit("name", ctx)
        uid?.visit("uid", ctx)
        links.forEach { it.visit("links", ctx) }
        archetypeDetails?.visit("archetype_details", ctx)
        feederAudit?.visit("feeder_audit", ctx)
        archetypeNodeId?.let { ctx.visitValue("archetype_node_id", it) }

        // Visit own properties
        folders.forEach { it.visit("folders", ctx) }
        items.forEach { it.visit("items", ctx) }
        details?.visit("details", ctx)

        ctx.visitLocatable(attributeName, this, "FOLDER")
    }
}
