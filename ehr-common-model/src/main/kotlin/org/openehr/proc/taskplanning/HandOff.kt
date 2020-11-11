package org.openehr.proc.taskplanning

import care.better.platform.proc.taskplanning.visitor.TaskModelVisitor
import org.openehr.base.basetypes.UidBasedId

/**
 * @author Primoz Delopst
 */
class HandOff() : DispatchableAction(), LinkedPlan {

    private var target: TaskPlan? = null
    private var targetUid: UidBasedId? = null

    constructor(target: TaskPlan?) : this() {
        this.target = target
    }

    constructor(targetUid: UidBasedId?) : this() {
        this.targetUid = targetUid
    }

    override fun getTarget(): TaskPlan? = target

    override fun setTarget(target: TaskPlan?) {
        this.target = target
    }

    override fun getTargetUid(): UidBasedId? = targetUid

    override fun setTargetUid(targetUid: UidBasedId?) {
        this.targetUid = targetUid
    }

    override fun addSubjectPrecondition(subjectPrecondition: SubjectPrecondition): HandOff = super.addSubjectPrecondition(subjectPrecondition) as HandOff

    override fun accept(visitor: TaskModelVisitor) {
        visitor.visit(this)
        visitor.afterVisit(this)
        acceptPreconditions(visitor)
        target?.also { it.accept(visitor) }
        visitor.afterAccept(this)
    }

    override fun toString(): String =
            "HandOff{" +
                    "target=$target" + target +
                    "targetUid=$targetUid" + targetUid +
                    "} ${super.toString()}"
}