package org.openehr.proc.taskplanning

import care.better.openehr.rm.RmObject
import java.io.Serializable

/**
 * @author Primoz Delopst
 */
class PlanCalendar : RmObject(), Serializable {

    var entries: MutableList<CalendarEntry> = mutableListOf()

    fun addEntry(entry: CalendarEntry): PlanCalendar = entries.add(entry).let { this }

    override fun toString(): String =
            "PlanCalendar{" +
                    "entries=$entries" +
                    '}'
}