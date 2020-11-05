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

package org.openehr.proc.taskplanning

import care.better.platform.annotation.Open
import care.better.platform.annotation.Required
import care.better.platform.proc.taskplanning.visitor.TaskModelVisitor
import care.better.platform.proc.taskplanning.visitor.VisitableByModelVisitor
import org.openehr.base.basetypes.LocatableRef
import org.openehr.base.basetypes.UidBasedId
import org.openehr.rm.composition.ContentItem
import org.openehr.rm.datastructures.ItemStructure
import org.openehr.rm.datatypes.DvText
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlType

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */
@XmlType(
    name = "WORK_PLAN", propOrder = [
        "description",
        "topLevelPlanUids",
        "topLevelPlans",
        "carePathway",
        "carePlan",
        "orderList",
        "calendar",
        "timeline",
        "context",
        "eventWaitStates",
        "indications",
        "classification"])
@Open
class WorkPlan() : ContentItem(), VisitableByModelVisitor {
    companion object {
        private const val serialVersionUID: Long = 0L
    }

    @XmlElement(required = true)
    @Required
    var description: DvText? = null

    @XmlElement(name = "care_pathway")
    var carePathway: ItemStructure? = null

    @XmlElement(name = "top_level_plan_uids")
    var topLevelPlanUids: LinkedHashSet<UidBasedId> = linkedSetOf()

    @XmlElement(name = "top_level_plans")
    var topLevelPlans: LinkedHashSet<TaskPlan> = linkedSetOf()

    @XmlElement(name = "care_plan")
    var carePlan: LocatableRef? = null

    @XmlElement(name = "order_list")
    var orderList: MutableList<OrderRef> = mutableListOf()

    @XmlElement(required = true)
    var calendar: PlanCalendar = PlanCalendar()

    @XmlElement(required = true)
    var timeline: PlanTimeline = PlanTimeline()

    var context: PlanDataContext? = null

    @XmlElement(name = "event_wait_states")
    var eventWaitStates: MutableList<EventWait<out PlanEvent>> = mutableListOf()

    @XmlElement(name = "indications")
    var indications: MutableList<DvText> = mutableListOf()

    var classification: ItemStructure? = null

    constructor(description: DvText?) : this() {
        this.description = description
    }

    constructor(description: DvText?, context: PlanDataContext?) : this(description) {
        this.context = context
    }

    override fun accept(visitor: TaskModelVisitor) {
        visitor.visit(this)
        visitor.afterVisit(this)
        context?.also { it.accept(visitor) }
        orderList.forEach { it.accept(visitor) }
        topLevelPlans.forEach { it.accept(visitor) }
        visitor.afterAccept(this)
    }

    override fun toString(): String =
        "WorkPlan{" +
                "description=$description" +
                ", carePathway=$carePathway" +
                ", topLevelPlanUids=$topLevelPlanUids" +
                ", topLevelPlans=$topLevelPlans" +
                ", carePlan=$carePlan" +
                ", orderList=$orderList" +
                ", calendar=$calendar" +
                ", timeline=$timeline" +
                ", context=$context" +
                ", eventWaitStates=$eventWaitStates" +
                ", name=$name" +
                ", uid=$uid" +
                ", indications=$indications" +
                ", archetypeDetails=$archetypeDetails" +
                ", archetypeNodeId='$archetypeNodeId'" +
                ", classification=$classification" +
                '}'
}
