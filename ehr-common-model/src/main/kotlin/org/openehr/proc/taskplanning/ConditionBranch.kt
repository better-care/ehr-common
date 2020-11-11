package org.openehr.proc.taskplanning

import care.better.platform.proc.taskplanning.visitor.TaskModelVisitor
import org.openehr.rm.datatypes.DvText

/**
 * @author Primoz Delopst
 */

class ConditionBranch : ChoiceBranch<PlanItem>, ExpressionNamesProvider {

    lateinit var test: BooleanContextExpression

    constructor() : super()

    constructor(description: DvText, test: BooleanContextExpression) : super(description) {
        this.test = test
    }

    override fun setWaitSpec(waitSpec: TaskWait?) {
        if (waitSpec != null) {
            throw UnsupportedOperationException("Wait Spec not supported on Condition Branches.")
        } else if (getWaitSpec() != null) {
            super.setWaitSpec(null)
        }
    }

    override fun addMember(member: PlanItem): ConditionBranch = super.addMember(member) as ConditionBranch

    override fun addExecutionRule(executionRule: ExecutionRule): ConditionBranch = super.addExecutionRule(executionRule) as ConditionBranch

    override fun accept(visitor: TaskModelVisitor) {
        visitor.visit(this)
        visitor.afterVisit(this)
        acceptRepeatAndWaitSpec(visitor)
        acceptReviewDataset(visitor)
        acceptExecutionRules(visitor)
        test.also { it.accept(visitor) }
        acceptMembers(visitor)
        visitor.afterAccept(this)
    }

    override fun getExpressionNames(): Sequence<String> = listOf(test.name).asSequence()

    override fun toString(): String =
            "ConditionBranch{" +
                    "test=$test" + test +
                    "} ${super.toString()}"
}