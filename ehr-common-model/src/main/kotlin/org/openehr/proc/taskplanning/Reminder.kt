package org.openehr.proc.taskplanning

/**
 * @author Primoz Delopst
 */
class Reminder : EventWait<PlanEvent> {

    constructor()

    constructor(event: PlanEvent?) : super(event)

    constructor(event: PlanEvent?, successAction: EventAction?) : super(event, successAction, null)

    override fun toString(): String = "Reminder{} ${super.toString()}"
}