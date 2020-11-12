package org.openehr.proc.taskplanning

import care.better.openehr.rm.RmObject
import care.better.platform.proc.taskplanning.visitor.VisitableByModelVisitor

/**
 * @author Primoz Delopst
 */
abstract class ContextValue<T>() : RmObject(), VisitableByModelVisitor {
    var name: String? = null
    private var type: ExprTypeDef<T>? = null

    protected constructor(type: ExprTypeDef<T>?) : this() {
        this.type = type
    }

    protected constructor(name: String?, type: ExprTypeDef<T>?) : this(type) {
        this.name = name
    }

    open fun getType(): ExprTypeDef<T>? = type

    open fun setType(type: ExprTypeDef<T>?) {
        this.type = type
    }

    override fun toString(): String =
            "ContextValue{" +
                    "name='$name'" +
                    ", type=$type" +
                    "} ${super.toString()}"
}