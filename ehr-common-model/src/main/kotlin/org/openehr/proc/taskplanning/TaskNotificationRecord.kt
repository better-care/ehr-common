package org.openehr.proc.taskplanning

import care.better.openehr.rm.RmObject
import kotlinx.serialization.Serializable
import org.openehr.rm.common.PartyProxy
import java.util.LinkedHashMap

/**
 * @author Primoz Delopst
 */

@Serializable
class TaskNotificationRecord : RmObject() {
    lateinit var receiver: PartyProxy
    lateinit var receiverTaskPlan: String

    var details: Map<String, String> = LinkedHashMap()
}