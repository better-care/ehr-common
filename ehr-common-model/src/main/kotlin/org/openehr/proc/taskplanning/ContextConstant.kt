package org.openehr.proc.taskplanning

import care.better.platform.proc.taskplanning.visitor.TaskModelVisitor

/**
 * @author Primoz Delopst
 */

class ContextConstant<T : Any> : ContextValue<T> {
    lateinit var value: T

    constructor() : super()

    constructor(name: String, type: ExprTypeDef<T>, value: T) : super(name, type) {
        this.value = value
    }

    override fun accept(visitor: TaskModelVisitor) {
        visitor.visit(this)
        visitor.afterVisit(this)
        visitor.afterAccept(this)
    }

    override fun toString(): String =
            "ContextConstant{" +
                    "value=${value}" + value +
                    "} ${super.toString()}"
}