package org.openehr.proc.taskplanning

import care.better.platform.proc.taskplanning.visitor.TaskModelVisitor

/**
 * @author Primoz Delopst
 */
abstract class ExternalVariable<T> : ContextVariable<T> {
    var populatingRequest: SystemCall? = null

    constructor()

    protected constructor(type: ExprTypeDef<T>?, name: String?) : super(name, type)

    protected constructor(type: ExprTypeDef<T>?, name: String?, populatingRequest: SystemCall?) : this(type, name) {
        this.populatingRequest = populatingRequest
    }

    override fun accept(visitor: TaskModelVisitor) {
        visitor.visit(this)
        visitor.afterVisit(this)
        populatingRequest?.also { it.accept(visitor) }
        visitor.afterAccept(this)
    }

    override fun toString(): String =
            "ExternalVariable{" +
                    "populatingRequest=$populatingRequest" +
                    "} ${super.toString()}"
}