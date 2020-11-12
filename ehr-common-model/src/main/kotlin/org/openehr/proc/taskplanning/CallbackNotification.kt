package org.openehr.proc.taskplanning

import care.better.platform.annotation.RequiresNotNull
import org.openehr.base.basetypes.UidBasedId

/**
 * @author Primoz Delopst
 */

class CallbackNotification() : PlanEvent(), TaskReferencingEvent {
    @RequiresNotNull
    private var taskId: UidBasedId? = null

    var requestId: String? = null

    var subjectId: String? = null

    var manuallyNotified: Boolean? = null

    constructor(taskId: UidBasedId?) : this() {
        this.taskId = taskId
    }

    constructor(taskId: UidBasedId?, requestId: String?) : this(taskId) {
        this.requestId = requestId
    }

    override fun getTaskId(): UidBasedId? = taskId

    fun setTaskId(taskId: UidBasedId?) {
        this.taskId = taskId
    }

    override fun toString(): String =
            "CallbackNotification{" +
                    "taskId=" +
                    ", requestId='$requestId'" +
                    ", subjectId='$subjectId'" +
                    ", manuallyNotified=$manuallyNotified'" +
                    "} ${super.toString()}"
}