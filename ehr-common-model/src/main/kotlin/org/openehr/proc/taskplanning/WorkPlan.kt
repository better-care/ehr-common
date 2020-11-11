package org.openehr.proc.taskplanning

import care.better.platform.proc.taskplanning.visitor.TaskModelVisitor
import care.better.platform.proc.taskplanning.visitor.VisitableByModelVisitor
import org.openehr.base.basetypes.LocatableRef
import org.openehr.base.basetypes.UidBasedId
import org.openehr.rm.composition.ContentItem
import org.openehr.rm.datastructures.ItemStructure
import org.openehr.rm.datatypes.DvText

/**
 * @author Primoz Delopst
 */
class WorkPlan() : ContentItem(), VisitableByModelVisitor {

    lateinit var description: DvText
    var carePathway: ItemStructure? = null
    var topLevelPlanUids: LinkedHashSet<UidBasedId> = LinkedHashSet()
    var topLevelPlans: LinkedHashSet<TaskPlan> = LinkedHashSet()
    var carePlan: LocatableRef? = null
    var orderList: MutableList<OrderRef> = mutableListOf()
    var calendar: PlanCalendar = PlanCalendar()
    var timeline: PlanTimeline = PlanTimeline()
    var context: PlanDataContext? = null
    var eventWaitStates: MutableList<EventWait<out PlanEvent>> = mutableListOf()
    var indications: MutableList<DvText> = mutableListOf()
    var classification: ItemStructure? = null

    constructor(description: DvText) : this() {
        this.description = description
    }

    constructor(description: DvText, context: PlanDataContext) : this(description) {
        this.context = context
    }

    fun addTopLevelPlanUid(planId: UidBasedId): WorkPlan = topLevelPlanUids.add(planId).let { this }

    fun addTopLevelPlanUids(planIds: Set<UidBasedId>): WorkPlan = topLevelPlanUids.addAll(planIds).let { this }

    fun addOrderList(orderList: OrderRef): WorkPlan = this.orderList.add(orderList).let { this }

    fun setTopLevelPlans(topLevelPlans: Set<TaskPlan>) {
        if (topLevelPlans.isNotEmpty()) {
            this.topLevelPlans.addAll(topLevelPlans)
        } else {
            this.topLevelPlans.clear()
        }
    }

    fun addTopLevelPlan(topLevelPlan: TaskPlan): WorkPlan = topLevelPlans.add(topLevelPlan).let { this }

    fun addEventWaitState(eventWaitState: EventWait<out PlanEvent>): WorkPlan = eventWaitStates.add(eventWaitState).let { this }

    fun addIndication(indication: DvText): WorkPlan = indications.add(indication).let { this }

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