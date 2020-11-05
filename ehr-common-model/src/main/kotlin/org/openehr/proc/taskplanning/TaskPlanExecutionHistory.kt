package org.openehr.proc.taskplanning

import care.better.openehr.rm.RmObject
import kotlinx.serialization.Serializable

/**
 * @author Primoz Delopst
 */

@Serializable
class TaskPlanExecutionHistory : RmObject() {
    val taskEvents: MutableList<TaskEventRecord> = mutableListOf()

    val planEvents: MutableList<TaskPlanEventRecord> = mutableListOf()
}