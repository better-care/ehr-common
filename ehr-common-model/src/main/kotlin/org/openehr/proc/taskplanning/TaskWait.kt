package org.openehr.proc.taskplanning

import care.better.openehr.rm.RmObject
import java.io.Serializable

/**
 * @author Primoz Delopst
 */
class TaskWait() : RmObject(), Serializable {
    var events: MutableList<PlanEvent> = mutableListOf()
    var eventRelation: TemporalRelation? = null
    var timeout: TimerWait? = null
    var nextState: TaskLifecycle? = null

    constructor(eventRelation: TemporalRelation?, timeout: TimerWait) : this() {
        this.eventRelation = eventRelation
        this.timeout = timeout
    }

    fun addEvent(event: PlanEvent): TaskWait = events.add(event).let { this }

    override fun toString(): String =
            "TaskWait{" +
                    "events=$events" +
                    ", eventRelation=$eventRelation" +
                    ", timeout=$timeout" +
                    ", nextState=$nextState" +
                    '}'
}