package org.openehr.proc.taskplanning

import care.better.platform.proc.taskplanning.visitor.TaskModelVisitor
import org.openehr.rm.datatypes.DvText

/**
 * @author Primoz Delopst
 */

class AdhocGroup : ChoiceGroup<AdhocBranch> {
    constructor() : super()

    constructor(description: DvText) : super(description)

    constructor(description: DvText, overrideType: OverrideType) : this(description) {
        this.overrideType = overrideType
    }

    override fun addMember(member: AdhocBranch): AdhocGroup = super.addMember(member) as AdhocGroup

    override fun addExecutionRule(executionRule: ExecutionRule): AdhocGroup = super.addExecutionRule(executionRule) as AdhocGroup

    override fun accept(visitor: TaskModelVisitor) {
        visitor.visit(this)
        visitor.afterVisit(this)
        acceptRepeatAndWaitSpec(visitor)
        acceptReviewDataset(visitor)
        acceptExecutionRules(visitor)
        acceptMembers(visitor)
        visitor.afterAccept(this)
    }

    override fun toString(): String = "AdhocGroup{} ${super.toString()}"

}