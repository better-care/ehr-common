package org.openehr.proc.taskplanning

import care.better.platform.proc.taskplanning.visitor.TaskModelVisitor

/**
 * @author Primoz Delopst
 */
open class DispatchableAction : TaskAction() {
    override fun addSubjectPrecondition(subjectPrecondition: SubjectPrecondition): DispatchableAction =
            super.addSubjectPrecondition(subjectPrecondition) as DispatchableAction

    override fun accept(visitor: TaskModelVisitor) {
        visitor.visit(this)
        visitor.afterVisit(this)
        acceptPreconditions(visitor)
        visitor.afterAccept(this)
    }

    override fun toString(): String =
            "DispatchableAction{" +
                    "} ${super.toString()}"
}