package org.openehr.proc.taskplanning

import care.better.platform.annotation.RequiresNotNull
import org.openehr.base.basetypes.LocatableRef

/**
 * @author Primoz Delopst
 */

class TaskEventRecord : EventRecord {
    @RequiresNotNull
    var taskId: String? = null

    @RequiresNotNull
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

    fun addNotificationSet(notificationSent: TaskNotificationRecord): TaskEventRecord = notificationsSent.add(notificationSent).let { this }

    fun addEntryInstance(entryInstance: LocatableRef): TaskEventRecord = entryInstances.add(entryInstance).let { this }

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