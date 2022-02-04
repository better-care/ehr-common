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
import org.openehr.base.basetypes.UidBasedId
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlType

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TASK_TRANSITION", propOrder = [
    "taskId",
    "transitions"])
@Open
class TaskTransition() : PlanEvent(), TaskReferencingEvent {
    companion object {
        private const val serialVersionUID: Long = 0L
    }

    @XmlElement(name = "task_id", required = true)
    @Required
    private var taskId: UidBasedId? = null

    @XmlElement(name = "transitions", required = true)
    @Required
    var transitions: MutableList<TaskLifecycle> = mutableListOf()

    constructor(taskId: UidBasedId?) : this() {
        this.taskId = taskId
    }

    constructor(taskId: UidBasedId?, vararg transitions: TaskLifecycle) : this(taskId) {
        this.transitions = transitions.toMutableList()
    }

    override fun getTaskId(): UidBasedId? = taskId

    fun setTaskId(taskId: UidBasedId?) {
        this.taskId = taskId
    }

    override fun toString(): String =
        "TaskTransition{" +
                "taskId=$taskId" +
                ", transitions=$transitions" +
                "} ${super.toString()}"
}
