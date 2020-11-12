package org.openehr.proc.taskplanning

import care.better.openehr.rm.RmObject
import care.better.platform.annotation.RequiresNotNull
import java.io.Serializable

/**
 * @author Primoz Delopst
 */
open class EventWait<E : PlanEvent>() : RmObject(), Serializable {
    @RequiresNotNull
    var event: E? = null
    var successAction: EventAction? = null
    var timeout: TimerWait? = null

    constructor(event: E?) : this() {
        this.event = event
    }

    constructor(event: E?, successAction: EventAction?, timeout: TimerWait?) : this(event) {
        this.successAction = successAction
        this.timeout = timeout
    }

    override fun toString(): String =
            "EventWait{" +
                    "event=$event" +
                    ", successAction=$successAction" +
                    ", timeout=$timeout" +
                    "} ${super.toString()}"
}