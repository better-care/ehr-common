package org.openehr.proc.taskplanning

import care.better.platform.proc.taskplanning.visitor.TaskModelVisitor

/**
 * @author Primoz Delopst
 */

class CaptureDatasetSpec : DatasetSpec() {

    var commitGroup: DatasetCommitGroup? = null

    override fun accept(visitor: TaskModelVisitor) {
        visitor.visit(this)
        visitor.afterVisit(this)
        populatingCall?.also { it.accept(visitor) }
        commitGroup?.also { it.accept(visitor) }
        visitor.afterAccept(this)
    }

    override fun toString(): String =
            "CaptureDatasetSpec{" +
                    "commitGroup=$commitGroup" +
                    "} ${super.toString()}"

}