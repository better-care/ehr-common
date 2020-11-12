package org.openehr.proc.taskplanning

import care.better.platform.proc.taskplanning.visitor.TaskModelVisitor
import org.openehr.rm.datatypes.DvText

/**
 * @author Primoz Delopst
 */
abstract class ChoiceBranch<I : PlanItem> : TaskGroup<I> {

    constructor()

    protected constructor(executionType: ExecutionType?) : super(executionType)

    protected constructor(description: DvText?, executionType: ExecutionType?) : super(description, executionType)

    protected constructor(description: DvText?) : super(description)

    override fun accept(visitor: TaskModelVisitor) {
        visitor.visit(this)
        visitor.afterVisit(this)
        acceptRepeatAndWaitSpec(visitor)
        acceptReviewDataset(visitor)
        acceptExecutionRules(visitor)
        acceptMembers(visitor)
        visitor.afterAccept(this)
    }

    override fun toString(): String = "ChoiceBranch{} ${super.toString()}"
}