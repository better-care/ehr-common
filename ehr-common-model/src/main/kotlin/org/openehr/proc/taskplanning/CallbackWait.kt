package org.openehr.proc.taskplanning

/**
 * @author Primoz Delopst
 */
class CallbackWait :  EventWait<CallbackNotification>(){

    var failAction: EventAction? = null
    var customActions: Map<String, EventAction>? = null
}