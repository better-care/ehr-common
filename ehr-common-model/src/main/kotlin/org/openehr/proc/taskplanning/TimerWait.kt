package org.openehr.proc.taskplanning

/**
 * @author Primoz Delopst
 */
class TimerWait : EventWait<TimerEvent> {

    constructor()

    constructor(event: TimerEvent?) : super(event)

    constructor(event: TimerEvent?, successAction: EventAction?, timeout: TimerWait?) : super(event, successAction, timeout)

    override fun toString(): String = "TimerWait{} ${super.toString()}"
}