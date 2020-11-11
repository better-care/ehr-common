package org.openehr.proc.taskplanning

import care.better.platform.proc.taskplanning.visitor.TaskModelVisitor
import org.openehr.rm.datatypes.DvText

/**
 * @author Primoz Delopst
 */
abstract class Task<A : TaskAction> : PlanItem {

    lateinit var action: A
    var orderTags: MutableList<String> = mutableListOf()


    constructor() : super()

    protected constructor(action: A) : this() {
        this.action = action
    }

    protected constructor(description: DvText, action: A) : super(description) {
        this.action = action
    }

    protected constructor(description: DvText, repeatSpec: TaskRepeat?, waitSpec: TaskWait?, action: A) : super(description, repeatSpec, waitSpec) {
        this.action = action
    }

    override fun accept(visitor: TaskModelVisitor) {
        visitor.visit(this)
        visitor.afterVisit(this)
        acceptRepeatAndWaitSpec(visitor)
        acceptReviewDataset(visitor)
        action.accept(visitor)
        visitor.afterAccept(this)
    }

    override fun toString(): String =
            "Task{" +
                    "action=$action" +
                    ", orderTags=$orderTags" +
                    '}'
}