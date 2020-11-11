package org.openehr.proc.taskplanning

import care.better.platform.proc.taskplanning.visitor.TaskModelVisitor
import org.openehr.rm.datatypes.DvText
import java.util.function.Consumer

/**
 * @author Primoz Delopst
 */
class PerformableTask<A : PerformableAction> : Task<A> {

    var captureDataset: MutableList<CaptureDatasetSpec> = mutableListOf()

    constructor() : super()

    constructor(action: A) : super(action)

    constructor(description: DvText, action: A) : super(description, action)

    constructor(description: DvText, repeatSpec: TaskRepeat?, waitSpec: TaskWait?, action: A) : super(description, repeatSpec, waitSpec, action)

    fun addCaptureDataset(captureDataset: CaptureDatasetSpec): PerformableTask<A> = this.captureDataset.add(captureDataset).let { this }

    override fun accept(visitor: TaskModelVisitor) {
        visitor.visit(this)
        visitor.afterVisit(this)
        acceptRepeatAndWaitSpec(visitor)
        acceptReviewDataset(visitor)
        acceptCaptureDataset(visitor)
        action.accept(visitor)
        visitor.afterAccept(this)
    }

    fun acceptCaptureDataset(visitor: TaskModelVisitor) {
        captureDataset.forEach{ it.accept(visitor) }
    }

    override fun toString(): String = "PerformableTask{} ${super.toString()}"
}