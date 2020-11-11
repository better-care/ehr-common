package org.openehr.proc.taskplanning

import care.better.platform.proc.taskplanning.visitor.TaskModelVisitor

/**
 * @author Primoz Delopst
 */

abstract class ContextVariable<T> : ContextValue<T> {

    constructor() : super()

    constructor(name: String, type: ExprTypeDef<T>) : super(name, type)

    override fun accept(visitor: TaskModelVisitor) {
        visitor.visit(this)
        visitor.afterVisit(this)
        visitor.afterAccept(this)
    }
}