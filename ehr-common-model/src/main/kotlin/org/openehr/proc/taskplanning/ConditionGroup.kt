package org.openehr.proc.taskplanning

import care.better.platform.proc.taskplanning.visitor.TaskModelVisitor
import org.openehr.rm.datatypes.DvText

/**
 * @author Primoz Delopst
 */

class ConditionGroup : ChoiceGroup<ConditionBranch> {

    constructor() : super()

    constructor(description: DvText) : super(description)

    override fun setWaitSpec(waitSpec: TaskWait?) {
        if (waitSpec != null) {
            throw UnsupportedOperationException("Wait Spec not supported on Condition Groups.")
        } else if (getWaitSpec() != null) {
            super.setWaitSpec(null)
        }
    }

    override fun addMember(member: ConditionBranch): ConditionGroup = super.addMember(member) as ConditionGroup

    override fun addExecutionRule(executionRule: ExecutionRule): ConditionGroup = super.addExecutionRule(executionRule) as ConditionGroup

    override fun accept(visitor: TaskModelVisitor) {
        visitor.visit(this)
        visitor.afterVisit(this)
        acceptRepeatAndWaitSpec(visitor)
        acceptReviewDataset(visitor)
        acceptExecutionRules(visitor)
        acceptMembers(visitor)
        visitor.afterAccept(this)
    }

    override fun toString(): String = "ConditionGroup{} ${super.toString()}"
}