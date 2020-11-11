package org.openehr.proc.taskplanning

import care.better.platform.proc.taskplanning.visitor.TaskModelVisitor
import org.openehr.base.basetypes.LocatableRef
import org.openehr.rm.composition.Entry

/**
 * @author Primoz Delopst
 */
class DefinedAction : PerformableAction {

    var prototype: MutableList<Entry> = mutableListOf()
    var optionality: ValidityKind? = null

    constructor() : super()

    constructor(optionality: ValidityKind?) : this() {
        this.optionality = optionality
    }

    constructor(instructionActivity: LocatableRef?, optionality: ValidityKind?) : super(instructionActivity) {
        this.optionality = optionality
    }

    constructor(instructionActivity: LocatableRef?, costingData: TaskCosting?, optionality: ValidityKind?) : super(instructionActivity, costingData) {
        this.optionality = optionality
    }

    fun addPrototype(prototype: Entry): DefinedAction = this.prototype.add(prototype).let { this }

    override fun addResource(resource: ResourceParticipation): DefinedAction = super.addResource(resource) as DefinedAction

    override fun addOtherParticipation(participation: TaskParticipation): DefinedAction = super.addOtherParticipation(participation) as DefinedAction

    override fun addSubjectPrecondition(subjectPrecondition: SubjectPrecondition): DefinedAction = super.addSubjectPrecondition(subjectPrecondition) as DefinedAction

    override fun accept(visitor: TaskModelVisitor) {
        visitor.visit(this)
        visitor.afterVisit(this)
        acceptPreconditions(visitor)
        acceptOtherParticipations(visitor)
        acceptResources(visitor)
        visitor.afterAccept(this)
    }

    override fun toString(): String =
            "DefinedAction{" +
                    "prototype=$prototype" +
                    ", optionality=$optionality" +
                    "} ${super.toString()}"
}