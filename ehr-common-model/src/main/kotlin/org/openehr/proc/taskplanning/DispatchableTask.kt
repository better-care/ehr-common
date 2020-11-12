package org.openehr.proc.taskplanning

import care.better.platform.proc.taskplanning.visitor.TaskModelVisitor
import org.openehr.rm.datatypes.DvText

/**
 * @author Primoz Delopst
 */

class DispatchableTask<A : DispatchableAction> : Task<A> {

    var wait = false
    var callback: CallbackWait? = null

    constructor()

    constructor(action: A?) : super(action)

    constructor(description: DvText?, action: A?) : super(description, action)

    constructor(description: DvText?, repeatSpec: TaskRepeat?, waitSpec: TaskWait?, action: A?) : super(description, repeatSpec, waitSpec, action)

    override fun accept(visitor: TaskModelVisitor) {
        visitor.visit(this)
        visitor.afterVisit(this)
        acceptRepeatAndWaitSpec(visitor)
        acceptReviewDataset(visitor)
        action?.accept(visitor)
        visitor.afterAccept(this)
    }

    override fun toString(): String =
            "DispatchableTask{" +
                    "wait=$wait" + wait +
                    ", callback=$callback" + callback +
                    "} ${super.toString()}"
}