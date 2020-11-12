package org.openehr.proc.taskplanning

import care.better.platform.proc.taskplanning.visitor.TaskModelVisitor

/**
 * @author Primoz Delopst
 */

class BooleanContextExpression : ContextExpression<Boolean> {

    constructor() : super(TypeDefBoolean.INSTANCE)

    constructor(expression: String?) : super(TypeDefBoolean.INSTANCE, expression)

    override fun getType(): TypeDefBoolean? = super.getType() as TypeDefBoolean?

    override fun setType(type: ExprTypeDef<Boolean>?) {
        if (TypeDefBoolean.INSTANCE != type) {
            throw UnsupportedOperationException("TypeDefBoolean required here.")
        }
        super.setType(type)
    }

    fun setType(type: TypeDefBoolean) {
        super.setType(type)
    }

    override fun accept(visitor: TaskModelVisitor) {
        visitor.visit(this)
        visitor.afterVisit(this)
        visitor.afterAccept(this)
    }

    override fun toString(): String = "BooleanContextExpression{} ${super.toString()}"
}