package org.openehr.proc.taskplanning

import care.better.openehr.rm.RmObject
import kotlinx.serialization.Serializable

/**
 * @author Primoz Delopst
 */

@Serializable
class TaskPlanExecutionHistory : RmObject() {
    var taskEvents: MutableList<TaskEventRecord> = mutableListOf()
    var planEvents: MutableList<TaskPlanEventRecord> = mutableListOf()

    fun addTaskEvent(taskEvent: TaskEventRecord): TaskPlanExecutionHistory = taskEvents.add(taskEvent).let { this }

    fun addPlanEvent(planEvent: TaskPlanEventRecord): TaskPlanExecutionHistory = planEvents.add(planEvent).let { this }

    override fun toString(): String =
            "TaskPlanExecutionHistory{" +
                    "taskEvents=$taskEvents" +
                    ", planEvents=$planEvents" +
                    '}'
}