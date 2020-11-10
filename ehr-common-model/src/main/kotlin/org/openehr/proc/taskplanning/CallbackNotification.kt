package org.openehr.proc.taskplanning

import kotlinx.serialization.Serializable
import org.openehr.base.basetypes.UidBasedId

/**
 * @author Primoz Delopst
 */

@Serializable
class CallbackNotification : PlanEvent(), TaskReferencingEvent {

    lateinit var taskId: UidBasedId

    var requestId: String? = null

    var subjectId: String? = null

    var manuallyNotified: Boolean? = null
}