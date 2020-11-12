package org.openehr.proc.taskplanning

import care.better.platform.annotation.RequiresNotNull
import org.openehr.rm.datastructures.ItemStructure

/**
 * @author Primoz Delopst
 */
class StateTrigger : PlanEvent, ExpressionNamesProvider {

    @RequiresNotNull
    var expression: BooleanContextExpression? = null

    constructor()

    constructor(expression: BooleanContextExpression?) {
        this.expression = expression
    }

    constructor(otherDetails: ItemStructure?, expression: BooleanContextExpression?) : super(otherDetails) {
        this.expression = expression
    }

    override fun getExpressionNames(): Sequence<String> = expression?.name?.let { listOf(it).asSequence() } ?: emptySequence()

    override fun toString(): String =
            "StateTrigger{" +
                    "expression=$expression" +
                    "} ${super.toString()}"
}