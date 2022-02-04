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
import org.openehr.base.basetypes.LocatableRef
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlType

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "TASK_EVENT_RECORD", propOrder = [
        "taskId",
        "lifecycleState",
        "preconditionsSatisfied",
        "waitConditionsSatisfied",
        "notificationsSent",
        "entryInstances",
        "lifecycleTransitionReason"])
@Open
class TaskEventRecord : EventRecord {
    companion object {
        private const val serialVersionUID: Long = 0L
    }

    @XmlElement(name = "task_id", required = true)
    @Required
    var taskId: String? = null

    @XmlElement(name = "lifecycle_state", required = true)
    @Required
    var lifecycleState: TaskLifecycle? = null

    @XmlElement(name = "notifications_sent")
    var notificationsSent: MutableList<TaskNotificationRecord> = mutableListOf()

    @XmlElement(name = "entry_instances")
    var entryInstances: MutableList<LocatableRef> = mutableListOf()

    @XmlElement(name = "preconditions_satisfied", required = true)
    var preconditionsSatisfied = false

    @XmlElement(name = "wait_conditions_satisfied", required = true)
    var waitConditionsSatisfied = false

    @XmlElement(name = "lifecycle_transition_reason")
    var lifecycleTransitionReason: String? = null

    constructor()

    constructor(time: String?, taskId: String?, lifecycleState: TaskLifecycle?) : super(time) {
        this.taskId = taskId
        this.lifecycleState = lifecycleState
    }

    override fun toString(): String =
        "TaskEventRecord{" +
                "taskId='$taskId'" +
                ", lifecycleState=$lifecycleState" +
                ", notificationsSent=$notificationsSent" + notificationsSent +
                ", entryInstances=$entryInstances" + entryInstances +
                ", preconditionsSatisfied=$preconditionsSatisfied" +
                ", waitConditionsSatisfied=$waitConditionsSatisfied" +
                ", lifecycleTransitionReason='$lifecycleTransitionReason'" +
                "} ${super.toString()}"
}
