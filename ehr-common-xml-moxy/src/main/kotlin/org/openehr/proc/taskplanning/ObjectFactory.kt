package org.openehr.proc.taskplanning

import javax.xml.bind.annotation.XmlRegistry

/**
 * @author Primoz Delopst
 */

@XmlRegistry
class ObjectFactory {
    fun createTaskEventRecord(): TaskEventRecord = TaskEventRecord()
    fun createTaskNotificationRecord(): TaskNotificationRecord = TaskNotificationRecord()
    fun createTaskPlanEventRecord(): TaskPlanEventRecord = TaskPlanEventRecord()
    fun createTaskPlanExecutionHistory(): TaskPlanExecutionHistory = TaskPlanExecutionHistory()
}