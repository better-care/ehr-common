package org.openehr.proc.taskplanning

import care.better.platform.proc.taskplanning.visitor.TaskModelVisitor
import care.better.platform.proc.taskplanning.visitor.VisitableByModelVisitor
import org.openehr.rm.common.Locatable
import org.openehr.rm.datastructures.ItemStructure
import org.openehr.rm.datatypes.DvText

/**
 * @author Primoz Delopst
 */

abstract class PlanItem : Locatable, VisitableByModelVisitor {

    lateinit var description: DvText
    var repeatSpec: TaskRepeat? = null
    var otherDetails: ItemStructure? = null
    private var waitSpec: TaskWait? = null
    var reviewDataset: MutableList<ReviewDatasetSpec> = mutableListOf()
    var classification: ItemStructure? = null
    var guidelineStep: String? = null
    var reminders: MutableList<Reminder> = mutableListOf()

    constructor() : super()

    protected constructor(description: DvText) : this() {
        this.description = description
    }

    protected constructor(description: DvText, repeatSpec: TaskRepeat?, waitSpec: TaskWait?) : this(description) {
        this.repeatSpec = repeatSpec
        this.waitSpec = waitSpec
    }

    fun getWaitSpec(): TaskWait? = waitSpec

    open fun setWaitSpec(waitSpec: TaskWait?) {
        this.waitSpec = waitSpec
    }

    fun addReviewDataset(reviewDataset: ReviewDatasetSpec): PlanItem = this.reviewDataset.add(reviewDataset).let { this }

    override fun accept(visitor: TaskModelVisitor) {
        visitor.visit(this)
        visitor.afterVisit(this)
        acceptRepeatAndWaitSpec(visitor)
        acceptReviewDataset(visitor)
        visitor.afterAccept(this)
    }

    protected open fun acceptRepeatAndWaitSpec(taskModelVisitor: TaskModelVisitor) {
        repeatSpec?.also { }
        waitSpec?.also { }
    }

    protected open fun acceptReviewDataset(taskModelVisitor: TaskModelVisitor) {
        reviewDataset.forEach { it.accept(taskModelVisitor) }
    }

    override fun toString(): String =
            "PlanItem{" +
                    "description=$description" +
                    ", repeatSpec=$repeatSpec" +
                    ", otherDetails=$otherDetails" +
                    ", waitSpec=$waitSpec" +
                    ", name=$name" +
                    ", uid=$uid" +
                    ", archetypeDetails=$archetypeDetails" +
                    ", archetypeNodeId='$archetypeNodeId" +
                    ", reviewDataset=$reviewDataset" +
                    ", classification=$classification" +
                    ", guidelineStep=$guidelineStep" +
                    ", reminders=$reminders" +
                    "}"
}