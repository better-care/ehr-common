package org.openehr.proc.taskplanning

import care.better.platform.proc.taskplanning.visitor.TaskModelVisitor
import org.openehr.rm.datatypes.DvText

/**
 * @author Primoz Delopst
 */

class EventBranch : ChoiceBranch<PlanItem> {

    constructor()

    constructor(executionType: ExecutionType?) : super(executionType)

    constructor(description: DvText?, executionType: ExecutionType?) : super(description, executionType)

    constructor(description: DvText?) : super(description)

    override fun setWaitSpec(waitSpec: TaskWait?) {
        if (waitSpec == null) {
            throw UnsupportedOperationException("Event Branches require a wait spec.")
        }
        super.setWaitSpec(waitSpec)
    }

    override fun addMember(member: PlanItem): EventBranch = super.addMember(member) as EventBranch

    override fun addExecutionRule(executionRule: ExecutionRule): EventBranch = super.addExecutionRule(executionRule) as EventBranch

    override fun accept(visitor: TaskModelVisitor) {
        visitor.visit(this)
        visitor.afterVisit(this)
        acceptRepeatAndWaitSpec(visitor)
        acceptReviewDataset(visitor)
        acceptExecutionRules(visitor)
        acceptMembers(visitor)
        visitor.afterAccept(this)
    }

    override fun toString(): String = "EventBranch{} ${super.toString()}"
}