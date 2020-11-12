package org.openehr.proc.taskplanning

import care.better.platform.proc.taskplanning.visitor.TaskModelVisitor

/**
 * @author Primoz Delopst
 */
class LocalVariable<T> : ContextVariable<T> {

    constructor()

    constructor(name: String?, type: ExprTypeDef<T>?) : super(name, type)

    override fun accept(visitor: TaskModelVisitor) {
        visitor.visit(this)
        visitor.afterVisit(this)
        visitor.afterAccept(this)
    }

    override fun toString(): String = "LocalVariable{} ${super.toString()}"
}