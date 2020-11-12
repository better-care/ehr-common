package org.openehr.proc.taskplanning

import care.better.openehr.rm.RmObject
import care.better.platform.annotation.RequiresNotNull
import care.better.platform.proc.taskplanning.visitor.TaskModelVisitor
import care.better.platform.proc.taskplanning.visitor.VisitableByModelVisitor
import java.io.Serializable

/**
 * @author Primoz Delopst
 */
class ParameterMapping() : RmObject(), Serializable, VisitableByModelVisitor {

    @RequiresNotNull
    var name: String? = null

    @RequiresNotNull
    var contextName: String? = null

    constructor(name: String?, contextName: String?) : this() {
        this.name = name
        this.contextName = contextName
    }

    override fun accept(visitor: TaskModelVisitor) {
        visitor.visit(this)
        visitor.afterVisit(this)
        visitor.afterAccept(this)
    }

    override fun toString(): String =
            "ParameterMapping{" +
                    "name='$name'" +
                    ", contextName='$contextName'" +
                    "} ${super.toString()}"
}