package org.openehr.proc.taskplanning

import care.better.platform.annotation.RequiresNotEmpty

/**
 * @author Primoz Delopst
 */

class TaskPlanEventRecord : EventRecord {

    @RequiresNotEmpty
    val details: LinkedHashMap<String, String> = LinkedHashMap()

    constructor()

    constructor(time: String?) : super(time)

    constructor(time: String?, description: String?) : super(time, description)

    fun addDetails(key: String, value: String): TaskPlanEventRecord {
        details[key] = value
        return this
    }

    override fun toString(): String =
            "TaskPlanEventRecord{" +
                    "details=$details" +
                    "} ${super.toString()}"
}