/* Copyright 2021 Better Ltd (www.better.care)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openehr.proc.taskplanning

import care.better.platform.annotation.Open
import care.better.platform.annotation.Required
import care.better.platform.proc.taskplanning.visitor.TaskModelVisitor
import org.openehr.rm.datatypes.DvText
import javax.xml.bind.annotation.*

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CHOICE_GROUP", propOrder = ["overrideType", "timeout"])
@XmlSeeAlso(value = [ConditionGroup::class, DecisionGroup::class, AdhocGroup::class, EventGroup::class])
@Open
abstract class ChoiceGroup<B : ChoiceBranch<out PlanItem>> : TaskGroup<B> {
    companion object {
        private const val serialVersionUID: Long = 0L
    }

    @XmlElements(value = [XmlElement(required = true, name = "overrideType"), XmlElement(required = true, name = "override_type")])
    @Required
    var overrideType: OverrideType? = null

    @XmlElement
    var timeout: TimerWait? = null

    constructor()

    constructor(description: DvText?) : super(description)

    constructor(description: DvText?, overrideType: OverrideType?) : this(description) {
        this.overrideType = overrideType
    }

    constructor(timeout: TimerWait?) {
        this.timeout = timeout
    }

    constructor(description: DvText?, timeout: TimerWait?) : this(description) {
        this.timeout = timeout
    }

    override fun setExecutionType(executionType: ExecutionType?) =
        if (executionType != null && executionType !== ExecutionType.PARALLEL)
            throw UnsupportedOperationException("Choice groups only support parallel type.")
        else
            super.setExecutionType(executionType)

    override fun accept(visitor: TaskModelVisitor) {
        val visited = visitor.visit(this)
        visitor.afterVisit(this)
        if (visited) {
            acceptRepeatAndWaitSpec(visitor)
            acceptReviewDataset(visitor)
            acceptExecutionRules(visitor)
            acceptMembers(visitor)
        }
        visitor.afterAccept(this)
    }

    override fun toString(): String =
        "ChoiceGroup{" +
                "overrideType=$overrideType" +
                ", timeout=$timeout" +
                "} ${super.toString()}"

}
