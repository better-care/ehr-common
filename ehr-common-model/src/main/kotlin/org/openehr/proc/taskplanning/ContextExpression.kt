package org.openehr.proc.taskplanning

import care.better.platform.proc.taskplanning.visitor.TaskModelVisitor

/**
 * @author Primoz Delopst
 */

open class ContextExpression<T> : ContextValue<T> {
    lateinit var expression: String

    constructor() : super()

    constructor(type: ExprTypeDef<T>) : super(type)

    constructor(type: ExprTypeDef<T>, expression: String) : this(type) {
        this.expression = expression
    }

    override fun accept(visitor: TaskModelVisitor) {
        visitor.visit(this)
        visitor.afterVisit(this)
        visitor.afterAccept(this)
    }

    override fun toString(): String =
            "ContextExpression{" +
                    "expression='$expression'" +
                    "} ${super.toString()}"
}