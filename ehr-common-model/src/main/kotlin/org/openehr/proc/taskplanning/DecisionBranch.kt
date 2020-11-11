package org.openehr.proc.taskplanning

import care.better.platform.proc.taskplanning.visitor.TaskModelVisitor
import org.openehr.rm.datatypes.DvText

/**
 * @author Primoz Delopst
 */
class DecisionBranch : ChoiceBranch<PlanItem>, ExpressionNamesProvider {

    lateinit var valueConstraint: BooleanContextExpression

    constructor() : super()

    constructor(description: DvText, valueConstraint: BooleanContextExpression) : super(description) {
        this.valueConstraint = valueConstraint
    }

    override fun setWaitSpec(waitSpec: TaskWait?) {
        if (waitSpec != null) {
            throw UnsupportedOperationException("Wait Spec not supported on Condition Branches.")
        } else if (getWaitSpec() != null) {
            super.setWaitSpec(null)
        }
    }

    override fun addMember(member: PlanItem): DecisionBranch = super.addMember(member) as DecisionBranch

    override fun addExecutionRule(executionRule: ExecutionRule): DecisionBranch = super.addExecutionRule(executionRule) as DecisionBranch

    override fun getExpressionNames(): Sequence<String> = listOf(valueConstraint.name).asSequence()

    override fun accept(visitor: TaskModelVisitor) {
        visitor.visit(this)
        visitor.afterVisit(this)
        acceptRepeatAndWaitSpec(visitor)
        acceptReviewDataset(visitor)
        acceptExecutionRules(visitor)
        valueConstraint.also { it.accept(visitor) }
        acceptMembers(visitor)
        visitor.afterAccept(this)
    }

    override fun toString(): String =
            "DecisionBranch{" +
                    "valueConstraint=$valueConstraint" + valueConstraint +
                    "} ${super.toString()}"
}