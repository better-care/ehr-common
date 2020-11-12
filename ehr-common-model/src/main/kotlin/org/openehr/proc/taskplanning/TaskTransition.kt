package org.openehr.proc.taskplanning

import care.better.platform.annotation.RequiresNotEmpty
import care.better.platform.annotation.RequiresNotNull
import org.openehr.base.basetypes.UidBasedId

/**
 * @author Primoz Delopst
 */
class TaskTransition() : PlanEvent(), TaskReferencingEvent {

    @RequiresNotNull
    private var taskId: UidBasedId? = null

    @RequiresNotEmpty
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