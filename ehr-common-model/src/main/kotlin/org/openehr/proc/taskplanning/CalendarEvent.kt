package org.openehr.proc.taskplanning

import kotlinx.serialization.Serializable
import org.openehr.base.basetypes.UidBasedId

/**
 * @author Primoz Delopst
 */

@Serializable
class CalendarEvent : PlanEvent() {

    var entryId: UidBasedId? = null

    lateinit var time: String
}