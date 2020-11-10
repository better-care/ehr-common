package org.openehr.proc.taskplanning

import care.better.platform.proc.taskplanning.visitor.TaskModelVisitor
import care.better.platform.proc.taskplanning.visitor.VisitableByModelVisitor
import org.openehr.base.basetypes.LocatableRef
import org.openehr.base.basetypes.UidBasedId
import org.openehr.rm.composition.ContentItem
import org.openehr.rm.composition.EventContext
import org.openehr.rm.datastructures.ItemStructure
import org.openehr.rm.datatypes.DvText
import java.util.*

/**
 * @author Primoz Delopst
 */
class WorkPlan() : ContentItem(), VisitableByModelVisitor {

    lateinit var description: DvText
    var carePathway: ItemStructure? = null
    var topLevelPlanUids: Set<UidBasedId> = LinkedHashSet<UidBasedId>()
    var topLevelPlans: Set<TaskPlan> = LinkedHashSet<TaskPlan>()
    var carePlan: LocatableRef? = null
    var orderList: MutableList<OrderRef> = mutableListOf()
    var calendar: PlanCalendar = PlanCalendar()
    var timeline: PlanTimeline = PlanTimeline()
    var context: PlanDataContext? = null
    var eventWaitStates: MutableList<EventWait<out PlanEvent?>> = mutableListOf()
    var indications: MutableList<DvText> = mutableListOf()
    var classification: ItemStructure? = null

    constructor(description: DvText) : this() {
        this.description = description
    }

    constructor(description: DvText, context: EventContext) : this(description) {
        this.context = context
    }

    override fun accept(visitor: TaskModelVisitor) {

    }

}