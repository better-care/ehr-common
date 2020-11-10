package org.openehr.proc.taskplanning

import care.better.platform.proc.taskplanning.visitor.TaskModelVisitor

/**
 * @author Primoz Delopst
 */

class ApiCall : SystemCall {

    constructor() : super()

    constructor(systemId: String, callName: String) : super(systemId, callName)

    constructor(systemId: String, callName: String, parameterMap: MutableList<ParameterMapping>) : super(systemId, callName, parameterMap)

    override fun accept(visitor: TaskModelVisitor) {
        visitor.visit(this)
        visitor.afterVisit(this)
        acceptParameters(visitor)
        visitor.afterAccept(this)
    }

    override fun toString(): String = "ApiCall{} ${super.toString()}"

}