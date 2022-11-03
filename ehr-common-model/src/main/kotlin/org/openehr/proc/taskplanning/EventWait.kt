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
import care.better.platform.annotation.Required
import java.io.Serializable
import javax.xml.bind.annotation.*

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EVENT_WAIT", propOrder = [
    "event",
    "successAction",
    "timeout"])
@XmlSeeAlso(value = [TimerWait::class, CallbackWait::class, Reminder::class])
@Open
class EventWait<E : PlanEvent>() : RmObject(), Serializable {
    companion object {
        private const val serialVersionUID: Long = 0L
    }

    @XmlElement(required = true)
    @Required
    var event: E? = null

    @XmlElement(name = "successAction")
    var successAction: EventAction? = null

    @XmlElement
    var timeout: TimerWait? = null

    constructor(event: E?) : this() {
        this.event = event
    }

    constructor(event: E?, successAction: EventAction?, timeout: TimerWait?) : this(event) {
        this.successAction = successAction
        this.timeout = timeout
    }

    override fun toString(): String =
        "EventWait{" +
                "event=$event" +
                ", successAction=$successAction" +
                ", timeout=$timeout" +
                "} ${super.toString()}"
}
