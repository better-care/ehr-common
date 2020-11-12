package org.openehr.proc.taskplanning

import care.better.platform.annotation.RequiresNotNull
import org.openehr.rm.datastructures.ItemStructure

/**
 * @author Primoz Delopst
 */
class TimerEvent : PlanEvent {

    @RequiresNotNull
    var duration: String? = null
    var purpose: String? = null

    constructor()

    constructor(duration: String?) {
        this.duration = duration
    }

    constructor(otherDetails: ItemStructure?, duration: String?, purpose: String?) : super(otherDetails) {
        this.duration = duration
        this.purpose = purpose
    }

    override fun toString(): String =
            "TimerEvent{" +
                    "duration=$duration" +
                    ", purpose='$purpose'" +
                    "} ${super.toString()}"
}