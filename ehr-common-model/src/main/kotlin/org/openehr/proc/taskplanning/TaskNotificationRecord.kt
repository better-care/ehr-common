package org.openehr.proc.taskplanning

import care.better.openehr.rm.RmObject
import org.openehr.rm.common.PartyProxy
import java.util.*

/**
 * @author Primoz Delopst
 */

class TaskNotificationRecord constructor() : RmObject() {
    lateinit var receiver: PartyProxy
    lateinit var receiverTaskPlan: String
    val details: LinkedHashMap<String, String> = LinkedHashMap()

    constructor(receiver: PartyProxy, receiverTaskPlan: String) : this() {
        this.receiver = receiver
        this.receiverTaskPlan = receiverTaskPlan
    }

    fun addDetails(key: String, value: String): TaskNotificationRecord {
        details[key] = value
        return this
    }

    override fun toString(): String =
            "TaskNotificationRecord{" +
                    "receiver= $receiver" +
                    ", receiverTaskPlan='$receiverTaskPlan'" +
                    ", details=$details" +
                    '}'
}