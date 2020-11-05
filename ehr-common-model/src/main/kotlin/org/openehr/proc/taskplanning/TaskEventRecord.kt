package org.openehr.proc.taskplanning

import kotlinx.serialization.Serializable
import org.openehr.base.basetypes.LocatableRef

/**
 * @author Primoz Delopst
 */

@Serializable
class TaskEventRecord : EventRecord() {
    lateinit var taskId: String
    lateinit var lifecycleState: TaskLifecycle
    var notificationsSent: MutableList<TaskNotificationRecord> = mutableListOf()
    var entryInstances: MutableList<LocatableRef> = mutableListOf()
    var preconditionsSatisfied = false
    var waitConditionsSatisfied = false
    var lifecycleTransitionReason: String? = null
}