package org.openehr.proc.taskplanning

import care.better.platform.proc.taskplanning.visitor.TaskModelVisitor

/**
 * @author Primoz Delopst
 */

open class EventVariable<T> : ExternalVariable<T> {

    constructor() : super()

    constructor(type: ExprTypeDef<T>, name: String) : super(type, name)

    constructor(type: ExprTypeDef<T>, name: String, populatingRequest: SystemCall) : super(type, name, populatingRequest)

    override fun accept(visitor: TaskModelVisitor) {
        visitor.visit(this)
        visitor.afterVisit(this)
        populatingRequest?.also { it.accept(visitor) }
        visitor.afterAccept(this)
    }

    override fun toString(): String = "EventVariable{} ${super.toString()}"

}