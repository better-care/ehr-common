package org.openehr.proc.taskplanning

import care.better.platform.proc.taskplanning.visitor.TaskModelVisitor
import org.openehr.rm.datatypes.DvText

/**
 * @author Primoz Delopst
 */
class EventGroup : ChoiceGroup<EventBranch> {

    constructor() : super()

    constructor(timeout: TimerWait?) : super(timeout)

    constructor(description: DvText, timeout: TimerWait?) : super(description, timeout)

    override fun addMember(member: EventBranch): EventGroup = super.addMember(member) as EventGroup

    override fun addExecutionRule(executionRule: ExecutionRule): EventGroup = super.addExecutionRule(executionRule) as EventGroup

    override fun accept(visitor: TaskModelVisitor) {
        visitor.visit(this)
        visitor.afterVisit(this)
        acceptRepeatAndWaitSpec(visitor)
        acceptReviewDataset(visitor)
        acceptExecutionRules(visitor)
        acceptMembers(visitor)
        visitor.afterAccept(this)
    }

    override fun toString(): String = "EventGroup{} ${super.toString()}"
}