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
@XmlType(
    name = "TASK_GROUP", propOrder = [
        "members",
        "executionType",
        "concurrencyMode",
        "executionRules",
        "trainingLevel"])
@XmlSeeAlso(value = [ChoiceGroup::class, ChoiceBranch::class])
@Open
class TaskGroup<I : PlanItem> : PlanItem {
    companion object {
        private const val serialVersionUID: Long = 0L
    }

    @XmlElement(required = true)
    @Required
    var members: MutableList<I> = mutableListOf()

    @XmlElement(name = "execution_type")
    private var executionType: ExecutionType? = null

    @XmlElement(name = "training_level")
    var trainingLevel: Int? = null

    @XmlElement(name = "execution_rules")
    var executionRules: MutableList<ExecutionRule> = mutableListOf()

    @XmlElement(name = "concurrency_mode")
    var concurrencyMode: ConcurrencyMode? = null

    constructor()

    constructor(executionType: ExecutionType?)

    constructor(description: DvText?) : super(description) {
        this.executionType = ExecutionType.SEQUENTIAL
    }

    constructor(description: DvText?, executionType: ExecutionType?) : super(description) {
        this.executionType = executionType
    }

    fun setExecutionType(executionType: ExecutionType?) {
        this.executionType = executionType
    }

    fun getExecutionType(): ExecutionType? = executionType


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
