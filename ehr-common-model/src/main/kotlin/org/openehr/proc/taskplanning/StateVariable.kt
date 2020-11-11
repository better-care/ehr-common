package org.openehr.proc.taskplanning

import care.better.platform.proc.taskplanning.visitor.TaskModelVisitor

/**
 * @author Primoz Delopst
 */
class StateVariable<T> : ExternalVariable<T> {

    var requiredCurrency: String? = null

    constructor() : super()

    constructor(type: ExprTypeDef<T>, name: String, populatingRequest: SystemCall, requiredCurrency: String?) : super(type, name, populatingRequest) {
        this.requiredCurrency = requiredCurrency
    }

    override fun accept(visitor: TaskModelVisitor) {
        visitor.visit(this)
        visitor.afterVisit(this)
        populatingRequest?.also { it.accept(visitor) }
        visitor.afterAccept(this)
    }

    override fun toString(): String =
            "StateVariable{" +
                    "requiredCurrency=$requiredCurrency" +
                    "} ${super.toString()}"
}