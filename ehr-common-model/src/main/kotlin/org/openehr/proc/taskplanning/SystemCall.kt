package org.openehr.proc.taskplanning

import care.better.openehr.rm.RmObject
import care.better.platform.proc.taskplanning.visitor.TaskModelVisitor
import care.better.platform.proc.taskplanning.visitor.VisitableByModelVisitor
import java.io.Serializable

/**
 * @author Primoz Delopst
 */
abstract class SystemCall constructor() : RmObject(), Serializable, VisitableByModelVisitor {

    lateinit var systemId: String
    lateinit var callName: String
    var parameterMap: MutableList<ParameterMapping> = mutableListOf()
    var boundParameters: MutableList<ParameterDef<*>> = mutableListOf()


    protected constructor(systemId: String, callName: String) : this() {
        this.systemId = systemId
        this.callName = callName
    }

    fun addParameter(name: String, contextName: String): SystemCall = parameterMap.add(ParameterMapping(name, contextName)).let { this }

    fun addBoundParameter(boundParameter: ParameterDef<*>): SystemCall = boundParameters.add(boundParameter).let { this }

    override fun accept(visitor: TaskModelVisitor) {
        visitor.visit(this)
        visitor.afterVisit(this)
        acceptParameters(visitor)
        visitor.afterAccept(this)
    }

    protected open fun acceptParameters(taskModelVisitor: TaskModelVisitor?) {
        parameterMap.forEach { it.accept(taskModelVisitor) }
        boundParameters.forEach { it.accept(taskModelVisitor) }
    }

    override fun toString(): String? {
        return "SystemCall{" +
                "systemId=$systemId" +
                ", callName='$callName" +
                ", parameterMap=$parameterMap" +
                ", boundParameters=$boundParameters" +
                "} ${super.toString()}"
    }
}