package org.openehr.proc.taskplanning

import care.better.platform.proc.taskplanning.visitor.TaskModelVisitor
import org.openehr.rm.datatypes.DvText

/**
 * @author Primoz Delopst
 */

class AdhocBranch : ChoiceBranch<PlanItem> {

    constructor()

    constructor(executionType: ExecutionType?) : super(executionType)

    constructor(description: DvText?, executionType: ExecutionType?) : super(description, executionType)

    constructor(description: DvText?) : super(description)

    override fun addMember(member: PlanItem): AdhocBranch = super.addMember(member) as AdhocBranch

    override fun addExecutionRule(executionRule: ExecutionRule): AdhocBranch = super.addExecutionRule(executionRule) as AdhocBranch

    override fun accept(visitor: TaskModelVisitor) {
        visitor.visit(this)
        visitor.afterVisit(this)
        acceptRepeatAndWaitSpec(visitor)
        acceptReviewDataset(visitor)
        acceptExecutionRules(visitor)
        acceptMembers(visitor)
        visitor.afterAccept(this)
    }

    override fun toString(): String = "AdhocBranch{} ${super.toString()}"
}