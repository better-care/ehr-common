package org.openehr.proc.taskplanning

import org.openehr.rm.datastructures.ItemStructure

/**
 * @author Primoz Delopst
 */
class TimelineMoment : PlanEvent {

    var timelineOffset: String? = null
    var fixedTime: TimeSpecifier? = null
    var timelineOrigin: PlanTimeOrigin? = null

    constructor() : super()

    constructor(timelineOffset: String?, fixedTime: TimeSpecifier?) : this(null, timelineOffset, fixedTime)

    constructor(otherDetails: ItemStructure?, timelineOffset: String?, fixedTime: TimeSpecifier?) : super(otherDetails) {
        this.timelineOffset = timelineOffset
        this.fixedTime = fixedTime
    }

    override fun toString(): String =
            "TimelineMoment{" +
                    "timelineOffset='$timelineOffset'" +
                    ", fixedTime=$fixedTime" +
                    ", timelineOrigin=$timelineOffset" +
                    "} ${super.toString()}"
}
