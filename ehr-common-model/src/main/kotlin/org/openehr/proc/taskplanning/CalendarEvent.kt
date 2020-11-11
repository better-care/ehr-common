package org.openehr.proc.taskplanning

import org.openehr.base.basetypes.UidBasedId

/**
 * @author Primoz Delopst
 */

class CalendarEvent constructor() : PlanEvent() {

    var entryId: UidBasedId? = null

    lateinit var time: String

    constructor(time: String) : this() {
        this.time = time
    }

    constructor(time: String, entryId: UidBasedId) : this(time){
        this.entryId = entryId
    }

    override fun toString(): String =
            "CalendarEvent{" +
                "entryId=$entryId" +
                ", time=$time" +
                "} ${super.toString()}"
}