/* Copyright 2021 Better Ltd (www.better.care)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openehr.proc.taskplanning

import care.better.openehr.rm.RmObject
import care.better.platform.annotation.Open
import org.openehr.rm.datatypes.DvText
import java.io.Serializable
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlType

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */
@XmlType(
    name = "EVENT_ACTION", propOrder = [
        "systemCall",
        "message",
        "resumeAction",
        "receiverThreadNextState"])
@Open
class EventAction() : RmObject(), Serializable {
    companion object {
        private const val serialVersionUID: Long = 0L
    }

    @XmlElement(name = "system_call")
    var systemCall: MutableList<SystemCall> = mutableListOf()

    var message: DvText? = null

    @XmlElement(name = "resume_action")
    var resumeAction: ResumeAction? = null

    @XmlElement(name = "receiver_thread_next_state")
    var receiverThreadNextState: TaskLifecycle? = null

    constructor(systemCall: MutableList<SystemCall>, message: DvText?, resumeAction: ResumeAction?, receiverThreadNextState: TaskLifecycle?) : this() {
        this.systemCall = systemCall
        this.message = message
        this.resumeAction = resumeAction
        this.receiverThreadNextState = receiverThreadNextState
    }

    override fun toString(): String =
        "EventAction{" +
                "systemCall=$systemCall" +
                ", message=$message" +
                ", resumeAction=$resumeAction" +
                ", receiverThreadNextState=$receiverThreadNextState" +
                '}'
}
