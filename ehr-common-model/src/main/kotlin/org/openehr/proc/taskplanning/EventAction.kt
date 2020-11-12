package org.openehr.proc.taskplanning

import care.better.openehr.rm.RmObject
import org.openehr.rm.datatypes.DvText
import java.io.Serializable

/**
 * @author Primoz Delopst
 */
class EventAction() : RmObject(), Serializable {

    var systemCall: MutableList<SystemCall> = mutableListOf()
    var message: DvText? = null
    var resumeAction: ResumeAction? = null
    var receiverThreadNextState: TaskLifecycle? = null

    constructor(systemCall: MutableList<SystemCall>, message: DvText?, resumeAction: ResumeAction?, receiverThreadNextState: TaskLifecycle?) : this() {
        this.systemCall = systemCall
        this.message = message
        this.resumeAction = resumeAction
        this.receiverThreadNextState = receiverThreadNextState
    }

    fun addSystemCall(systemCall: SystemCall): EventAction = this.systemCall.add(systemCall).let { this }

    override fun toString(): String =
            "EventAction{" +
                    "systemCall=$systemCall" +
                    ", message=$message" +
                    ", resumeAction=$resumeAction" +
                    ", receiverThreadNextState=$receiverThreadNextState" +
                    '}'
}