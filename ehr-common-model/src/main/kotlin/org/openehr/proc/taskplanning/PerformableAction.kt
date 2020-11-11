package org.openehr.proc.taskplanning

import care.better.platform.proc.taskplanning.visitor.TaskModelVisitor
import org.openehr.base.basetypes.LocatableRef

/**
 * @author Primoz Delopst
 */
abstract class PerformableAction : TaskAction {

    var resources: MutableList<ResourceParticipation> = mutableListOf()
    var otherParticipations: MutableList<TaskParticipation> = mutableListOf()

    constructor() : super()

    protected constructor(instructionActivity: LocatableRef?) : super(instructionActivity)

    protected constructor(instructionActivity: LocatableRef?, costingData: TaskCosting?) : super(instructionActivity, costingData)

    open fun addResource(resource: ResourceParticipation): PerformableAction = resources.add(resource).let { this }

    open fun addOtherParticipation(participation: TaskParticipation): PerformableAction = otherParticipations.add(participation).let { this }

    override fun addSubjectPrecondition(subjectPrecondition: SubjectPrecondition): PerformableAction =
            super.addSubjectPrecondition(subjectPrecondition) as PerformableAction

    override fun accept(visitor: TaskModelVisitor) {
        visitor.visit(this)
        visitor.afterVisit(this)
        acceptPreconditions(visitor)
        acceptOtherParticipations(visitor)
        acceptResources(visitor)
        visitor.afterAccept(this)
    }

    protected fun acceptOtherParticipations(visitor: TaskModelVisitor) {
        otherParticipations.forEach { it.accept(visitor) }
    }

    protected fun acceptResources(visitor: TaskModelVisitor) {
        resources.forEach { it.accept(visitor) }
    }


    override fun toString(): String =
            "PerformableAction{" +
                    "resources=$resources" +
                    ", otherParticipations=$otherParticipations" +
                    "} ${super.toString()}"
}