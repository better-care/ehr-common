package org.openehr.proc.taskplanning

import care.better.platform.annotation.RequiresNotNull
import care.better.platform.proc.taskplanning.visitor.TaskModelVisitor
import care.better.platform.proc.taskplanning.visitor.VisitableByModelVisitor
import org.openehr.rm.common.Locatable
import org.openehr.rm.common.PartyProxy
import org.openehr.rm.datatypes.DvCodedText
import org.openehr.rm.datatypes.DvText

/**
 * @author Primoz Delopst
 */
class TaskParticipation() : Locatable(), VisitableByModelVisitor {
    @RequiresNotNull
    var function: DvText? = null
    var role: MutableList<DvText> = mutableListOf()
    var mode: DvCodedText? = null
    var performer: PartyProxy? = null

    @RequiresNotNull
    var optionality: ValidityKind? = null

    constructor(function: DvText?, optionality: ValidityKind?) : this() {
        this.function = function
        this.optionality = optionality
    }

    fun addRole(role: DvText): TaskParticipation = this.role.add(role).let { this }

    override fun accept(visitor: TaskModelVisitor) {
        visitor.visit(this)
        visitor.afterVisit(this)
        visitor.afterAccept(this)
    }

    override fun toString(): String =
            "TaskParticipation{" +
                    "function=$function" +
                    ", role=$role" +
                    ", mode=$mode" +
                    ", performer=$performer" +
                    ", optionality=$optionality" +
                    ", name=$name" +
                    ", uid=$uid" +
                    ", archetypeDetails=$archetypeDetails" +
                    ", archetypeNodeId='$archetypeNodeId'" +
                    '}'
}