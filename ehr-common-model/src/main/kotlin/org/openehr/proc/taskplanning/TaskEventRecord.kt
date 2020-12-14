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

/**
 * @author Primoz Delopst
 */

@Open
class TaskEventRecord : EventRecord {
    @Required
    var taskId: String? = null

    @Required
    var lifecycleState: TaskLifecycle? = null
    var notificationsSent: MutableList<TaskNotificationRecord> = mutableListOf()
    var entryInstances: MutableList<LocatableRef> = mutableListOf()
    var preconditionsSatisfied = false
    var waitConditionsSatisfied = false
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