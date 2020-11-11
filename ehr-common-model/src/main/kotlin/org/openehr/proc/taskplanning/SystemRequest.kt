package org.openehr.proc.taskplanning

import care.better.platform.proc.taskplanning.visitor.TaskModelVisitor

/**
 * @author Primoz Delopst
 */
class SystemRequest() : DispatchableAction() {

    lateinit var systemCall: SystemCall

    constructor(systemCall: SystemCall) : this() {
        this.systemCall = systemCall
    }

    override fun addSubjectPrecondition(subjectPrecondition: SubjectPrecondition): SystemRequest =
            super.addSubjectPrecondition(subjectPrecondition) as SystemRequest

    override fun accept(visitor: TaskModelVisitor) {
        visitor.visit(this)
        visitor.afterVisit(this)
        acceptPreconditions(visitor)
        systemCall.accept(visitor)
        visitor.afterAccept(this)
    }

    override fun toString(): String =
            "SystemRequest{" +
                    "systemCall=$systemCall" +
                    "} ${super.toString()}"
}