package org.openehr.proc.taskplanning

import org.openehr.rm.datastructures.ItemStructure

/**
 * @author Primoz Delopst
 */
class TimerEvent : PlanEvent {

    lateinit var duration: String
    var purpose: String? = null

    constructor() : super()

    constructor(duration: String) : this() {
        this.duration = duration
    }

    constructor(otherDetails: ItemStructure?, duration: String, purpose: String?) : super(otherDetails) {
        this.duration = duration
        this.purpose = purpose
    }

    override fun toString(): String =
            "TimerEvent{" +
                    "duration=$duration" +
                    ", purpose='$purpose'" +
                    "} ${super.toString()}"
}