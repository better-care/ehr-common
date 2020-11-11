package org.openehr.proc.taskplanning

import care.better.platform.proc.taskplanning.visitor.TaskModelVisitor
import care.better.platform.proc.taskplanning.visitor.VisitableByModelVisitor
import org.openehr.base.basetypes.LocatableRef
import org.openehr.rm.common.Locatable

/**
 * @author Primoz Delopst
 */
abstract class TaskAction() : Locatable(), VisitableByModelVisitor {

    var instructionActivity: LocatableRef? = null
    var subjectPreconditions: MutableList<SubjectPrecondition> = mutableListOf()
    var costingData: TaskCosting? = null

    protected constructor(instructionActivity: LocatableRef?) : this() {
        this.instructionActivity = instructionActivity
    }

    protected constructor(instructionActivity: LocatableRef?, costingData: TaskCosting?) : this(instructionActivity) {
        this.costingData = costingData
    }

    open fun addSubjectPrecondition(subjectPrecondition: SubjectPrecondition): TaskAction = subjectPreconditions.add(subjectPrecondition).let { this }

    override fun accept(visitor: TaskModelVisitor) {
        visitor.visit(this)
        visitor.afterVisit(this)
        acceptPreconditions(visitor)
        visitor.afterAccept(this)
    }

    protected open fun acceptPreconditions(visitor: TaskModelVisitor) {
        subjectPreconditions.forEach { it.accept(visitor) }
    }

    override fun toString(): String =
            "TaskAction{" +
                ", instructionActivity=$instructionActivity" +
                ", subjectPreconditions=$subjectPreconditions" +
                ", costingData=$costingData" +
                ", name=$name" +
                ", uid=$uid" +
                ", archetypeDetails=$archetypeDetails" +
                ", archetypeNodeId='$archetypeNodeId'" +
                '}'
}