package org.openehr.proc.taskplanning

import care.better.openehr.rm.RmObject
import care.better.platform.annotation.RequiresNotNull
import care.better.platform.proc.taskplanning.visitor.TaskModelVisitor
import care.better.platform.proc.taskplanning.visitor.VisitableByModelVisitor
import java.io.Serializable

/**
 * @author Primoz Delopst
 */
class DatasetCommitGroup() : RmObject(), Serializable, VisitableByModelVisitor {

    @RequiresNotNull
    var groupId: String? = null
    var completionStep: Boolean = false

    constructor(groupId: String?, completionStep: Boolean) : this() {
        this.completionStep = completionStep
        this.groupId = groupId
    }

    override fun accept(visitor: TaskModelVisitor) {
        visitor.visit(this)
        visitor.afterVisit(this)
        visitor.afterAccept(this)
    }

    override fun toString(): String =
            "DatasetCommitGroup{" +
                    "groupId='$groupId'" +
                    ", completionStep=$completionStep" +
                    '}'
}