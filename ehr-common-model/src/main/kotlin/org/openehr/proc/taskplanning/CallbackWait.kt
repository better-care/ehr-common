package org.openehr.proc.taskplanning

/**
 * @author Primoz Delopst
 */
class CallbackWait : EventWait<CallbackNotification> {

    var failAction: EventAction? = null
    var customActions: MutableMap<String, EventAction> = mutableMapOf()

    constructor()

    constructor(event: CallbackNotification?) : super(event)

    constructor(event: CallbackNotification?, successAction: EventAction?, timeout: TimerWait?, failAction: EventAction?) : super(event, successAction, timeout) {
        this.failAction = failAction
    }

    override fun toString(): String =
            "CallbackWait{" +
                    "failAction=$failAction" +
                    ", customActions=$customActions" +
                    "} ${super.toString()}"
}