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

import care.better.openehr.rm.RmObject
import care.better.platform.annotation.Open
import care.better.platform.annotation.Required
import org.openehr.base.basetypes.UidBasedId
import org.openehr.proc.taskplanning.*
import org.openehr.rm.composition.Activity
import org.openehr.rm.composition.Composition
import org.openehr.rm.composition.ContentItem
import org.openehr.rm.datastructures.Event
import org.openehr.rm.datastructures.History
import org.openehr.rm.datastructures.Item
import org.openehr.rm.datastructures.ItemStructure
import org.openehr.rm.datatypes.DvText
import java.io.Serializable
import javax.xml.bind.annotation.*

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "LOCATABLE", propOrder = [
        "name",
        "uid",
        "links",
        "archetypeDetails",
        "feederAudit"])
@XmlSeeAlso(
    value = [
        Composition::class,
        Folder::class,
        History::class,
        Event::class,
        ItemStructure::class,
        Item::class,
        ContentItem::class,
        Activity::class,
        PlanEvent::class,
        PlanItem::class,
        TaskAction::class,
        TaskParticipation::class,
        OrderRef::class,
        DatasetSpec::class,
    ])
@XmlRootElement
@Open
abstract class Locatable : RmObject(), Serializable {
    companion object {
        private const val serialVersionUID: Long = 0L
    }

    @XmlElement(required = true)
    @Required
    var name: DvText? = null

    var uid: UidBasedId? = null

    var links: MutableList<Link> = mutableListOf()

    @XmlElement(name = "archetype_details")
    var archetypeDetails: Archetyped? = null

    @XmlElement(name = "feeder_audit")
    var feederAudit: FeederAudit? = null

    @XmlAttribute(name = "archetype_node_id", required = true)
    @Required
    var archetypeNodeId: String? = null
}
