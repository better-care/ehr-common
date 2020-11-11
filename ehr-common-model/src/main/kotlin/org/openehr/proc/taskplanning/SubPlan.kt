package org.openehr.proc.taskplanning

import care.better.platform.proc.taskplanning.visitor.TaskModelVisitor
import org.openehr.base.basetypes.LocatableRef
import org.openehr.base.basetypes.UidBasedId


/**
 * @author Primoz Delopst
 */
class SubPlan : PerformableAction, LinkedPlan {

    private var target: TaskPlan? = null
    private var targetUid: UidBasedId? = null

    constructor() : super()

    constructor(target: TaskPlan?) : this() {
        this.target = target
    }

    constructor(targetUid: UidBasedId?) : this() {
        this.targetUid = targetUid
    }

    constructor(instructionActivity: LocatableRef?, target: TaskPlan?) : super(instructionActivity) {
        this.target = target
    }

    constructor(instructionActivity: LocatableRef?, costingData: TaskCosting?, target: TaskPlan?) : super(instructionActivity, costingData) {
        this.target = target
    }

    constructor(instructionActivity: LocatableRef?, costingData: TaskCosting?, targetUid: UidBasedId?) : super(instructionActivity, costingData) {
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

    override fun addResource(resource: ResourceParticipation): SubPlan = super.addResource(resource) as SubPlan

    override fun addOtherParticipation(participation: TaskParticipation): SubPlan = super.addOtherParticipation(participation) as SubPlan

    override fun addSubjectPrecondition(subjectPrecondition: SubjectPrecondition): SubPlan = super.addSubjectPrecondition(subjectPrecondition) as SubPlan

    override fun accept(visitor: TaskModelVisitor) {
        visitor.visit(this)
        visitor.afterVisit(this)
        acceptPreconditions(visitor)
        acceptOtherParticipations(visitor)
        acceptResources(visitor)
        target?.also { it.accept(visitor) }
        visitor.afterAccept(this)
    }

    override fun toString(): String =
            "SubPlan{" +
                    "target=$target" +
                    "targetUid=$targetUid" +
                    "} ${super.toString()}"
}