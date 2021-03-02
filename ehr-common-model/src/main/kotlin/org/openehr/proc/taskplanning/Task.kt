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
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlSeeAlso
import javax.xml.bind.annotation.XmlType

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */
@XmlType(
    name = "TASK", propOrder = [
        "action",
        "orderTags"])
@XmlSeeAlso(value = [DispatchableTask::class, PerformableTask::class])
@Open
abstract class Task<A : TaskAction> : PlanItem {
    companion object {
        private const val serialVersionUID: Long = 0L
    }

    @XmlElement(required = true)
    @Required
    var action: A? = null

    @XmlElement(name = "order_tags")
    var orderTags: MutableList<String> = mutableListOf()

    constructor()

    protected constructor(action: A?) {
        this.action = action
    }

    protected constructor(description: DvText?, action: A?) : super(description) {
        this.action = action
    }

    protected constructor(description: DvText?, repeatSpec: TaskRepeat?, waitSpec: TaskWait?, action: A?) : super(description, repeatSpec, waitSpec) {
        this.action = action
    }

    override fun accept(visitor: TaskModelVisitor) {
        val visited = visitor.visit(this)
        visitor.afterVisit(this)
        if (visited) {
            acceptRepeatAndWaitSpec(visitor)
            acceptReviewDataset(visitor)
            action?.accept(visitor)
        }
        visitor.afterAccept(this)
    }

    override fun toString(): String =
        "Task{" +
                "action=$action" +
                ", orderTags=$orderTags" +
                '}'
}
