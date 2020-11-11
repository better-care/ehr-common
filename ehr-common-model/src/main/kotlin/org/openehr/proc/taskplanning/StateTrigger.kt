package org.openehr.proc.taskplanning

import org.openehr.rm.datastructures.ItemStructure

/**
 * @author Primoz Delopst
 */
class StateTrigger : PlanEvent, ExpressionNamesProvider {

    lateinit var expression: BooleanContextExpression

    constructor() : super()

    constructor(expression: BooleanContextExpression) : this() {
        this.expression = expression
    }

    constructor(otherDetails: ItemStructure?, expression: BooleanContextExpression) : super(otherDetails) {
        this.expression = expression
    }

    override fun getExpressionNames(): Sequence<String> = listOf(expression.name).asSequence()

    override fun toString(): String =
            "StateTrigger{" +
                    "expression=$expression" +
                    "} ${super.toString()}"
}