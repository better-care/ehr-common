package org.openehr.proc.taskplanning

import care.better.openehr.rm.RmObject
import care.better.platform.annotation.RequiresNotNull
import care.better.platform.proc.taskplanning.visitor.TaskModelVisitor
import care.better.platform.proc.taskplanning.visitor.VisitableByModelVisitor
import java.io.Serializable

/**
 * @author Primoz Delopst
 */
class ParameterDef<T : Any>() : RmObject(), Serializable, VisitableByModelVisitor {

    @RequiresNotNull
    var name: String? = null

    @RequiresNotNull
    var type: ExprTypeDef<T>? = null

    @RequiresNotNull
    var value: T? = null

    constructor(name: String?, type: ExprTypeDef<T>?) : this() {
        this.name = name
        this.type = type
    }

    constructor(name: String?, type: ExprTypeDef<T>?, value: T?) : this(name, type) {
        this.value = value
    }

    override fun accept(visitor: TaskModelVisitor) {
        visitor.visit(this)
        visitor.afterVisit(this)
        visitor.afterAccept(this)
    }

    override fun toString(): String =
            "ParameterDef{" +
                    "name='$name'" +
                    ", type=$type" +
                    ", value=$value" +
                    "} ${super.toString()}"
}