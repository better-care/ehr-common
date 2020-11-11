package org.openehr.proc.taskplanning

import care.better.platform.annotation.RequiresNotEmpty
import care.better.platform.proc.taskplanning.visitor.TaskModelVisitor
import org.openehr.rm.datatypes.DvText

/**
 * @author Primoz Delopst
 */
open class TaskGroup<I : PlanItem> : PlanItem {

    @RequiresNotEmpty
    var members: MutableList<I> = mutableListOf()
    private var executionType: ExecutionType? = null
    var trainingLevel: Int? = null
    var executionRules: MutableList<ExecutionRule> = mutableListOf()
    var concurrencyMode: ConcurrencyMode? = null

    constructor() : super()

    constructor(executionType: ExecutionType) : this()

    constructor(description: DvText) : super(description) {
        this.executionType = ExecutionType.SEQUENTIAL
    }

    constructor(description: DvText, executionType: ExecutionType) : super(description) {
        this.executionType = executionType
    }

    open fun setExecutionType(executionType: ExecutionType?){
        this.executionType = executionType
    }

    fun getExecutionType(): ExecutionType? = executionType

    open fun addMember(member: I): TaskGroup<I> = members.add(member).let { this }

    open fun addExecutionRule(executionRule: ExecutionRule): TaskGroup<I> = executionRules.add(executionRule).let { this }

    override fun accept(visitor: TaskModelVisitor) {
        visitor.visit(this)
        visitor.afterVisit(this)
        acceptRepeatAndWaitSpec(visitor)
        acceptReviewDataset(visitor)
        acceptExecutionRules(visitor)
        acceptMembers(visitor)
        visitor.afterAccept(this)
    }

    fun acceptExecutionRules(visitor: TaskModelVisitor) {
        executionRules.forEach { }
    }

    fun acceptMembers(visitor: TaskModelVisitor) {
        members.forEach { it.accept(visitor) }
    }

    override fun toString(): String =
            "TaskGroup{" +
                "members=$members" +
                ", executionType=$executionType" +
                ", trainingLevel=$trainingLevel" +
                ", executionRules=$executionRules" +
                ", concurrencyMode=$concurrencyMode" +
                "} ${super.toString()}"
}