package org.openehr.proc.taskplanning

import care.better.openehr.rm.RmObject
import java.io.Serializable

/**
 * @author Primoz Delopst
 */
class PlanTimeline : RmObject(), Serializable {

    var plannedItems: MutableList<PlanItem> = mutableListOf()
    var timers: MutableList<TimerWait> = mutableListOf()

    fun addPlannedItem(planItem: PlanItem): PlanTimeline = plannedItems.add(planItem).let { this }

    fun addTimer(timer: TimerWait): PlanTimeline = timers.add(timer).let { this }

    override fun toString(): String =
            "PlanTimeline{" +
                    "plannedItems=$plannedItems" +
                    ", timers=$timers" +
                    '}'
}