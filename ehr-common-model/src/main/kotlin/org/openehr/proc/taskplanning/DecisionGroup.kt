package org.openehr.proc.taskplanning

import care.better.platform.proc.taskplanning.visitor.TaskModelVisitor
import org.openehr.rm.datatypes.DvText

/**
 * @author Primoz Delopst
 */
class DecisionGroup : ChoiceGroup<DecisionBranch>, ExpressionNamesProvider {

    lateinit var test: ContextExpression<*>

    constructor() : super()

    constructor(test: ContextExpression<*>) : this() {
        this.test = test
    }

    constructor(description: DvText, test: ContextExpression<*>) : super(description) {
        this.test = test
    }

    override fun setWaitSpec(waitSpec: TaskWait?) {
        if (waitSpec != null) {
            throw UnsupportedOperationException("Wait Spec not supported on Decision Groups.")
        } else if (getWaitSpec() != null) {
            super.setWaitSpec(null)
        }
    }

    override fun addMember(member: DecisionBranch): DecisionGroup = super.addMember(member) as DecisionGroup

    override fun addExecutionRule(executionRule: ExecutionRule): DecisionGroup = super.addExecutionRule(executionRule) as DecisionGroup

    override fun getExpressionNames(): Sequence<String> = listOf(test.name).asSequence()

    override fun accept(visitor: TaskModelVisitor) {
        visitor.visit(this)
        visitor.afterVisit(this)
        acceptRepeatAndWaitSpec(visitor)
        acceptReviewDataset(visitor)
        acceptExecutionRules(visitor)
        test.accept(visitor)
        acceptMembers(visitor)
        visitor.afterAccept(this)
    }

    override fun toString(): String =
            "DecisionGroup{" +
                    "test=$test" +
                    "} ${super.toString()}"
}