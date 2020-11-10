package org.openehr.proc.taskplanning

import care.better.platform.proc.taskplanning.visitor.TaskModelVisitor
import org.openehr.rm.datatypes.DvText

/**
 * @author Primoz Delopst
 */

abstract class ChoiceGroup<B : ChoiceBranch<out PlanItem>> : TaskGroup<B> {
    lateinit var overrideType: OverrideType
    var timeout: TimerWait? = null

    constructor() : super()

    constructor(description: DvText) : super(description)

    constructor(description: DvText, overrideType: OverrideType) : this(description) {
        this.overrideType = overrideType
    }

    constructor(timeout: TimerWait?) : this() {
        this.timeout = timeout
    }

    constructor(description: DvText, timeout: TimerWait?) : this(description) {
        this.timeout = timeout
    }

    override fun setExecutionType(executionType: ExecutionType?) =
            if (executionType != null && executionType !== ExecutionType.PARALLEL)
                throw UnsupportedOperationException("Choice groups only support parallel type.")
            else
                super.setExecutionType(executionType)

    override fun accept(visitor: TaskModelVisitor) {
        visitor.visit(this)
        visitor.afterVisit(this)
        acceptRepeatAndWaitSpec(visitor)
        acceptReviewDataset(visitor)
        acceptExecutionRules(visitor)
        acceptMembers(visitor)
        visitor.afterAccept(this)
    }

    override fun toString(): String =
            "ChoiceGroup{" +
                    "overrideType=$overrideType" +
                    ", timeout=$timeout" +
                    "} ${super.toString()}"

}