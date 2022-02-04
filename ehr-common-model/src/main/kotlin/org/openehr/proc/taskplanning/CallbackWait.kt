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

import care.better.platform.annotation.Open
import care.better.platform.jaxb.MapStringAdapter
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlType
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CALLBACK_WAIT", propOrder = ["failAction", "customActions"])
@Open
class CallbackWait : EventWait<CallbackNotification> {
    companion object {
        private const val serialVersionUID: Long = 1L
    }

    @XmlElement(name = "event_action")
    var failAction: EventAction? = null

    @XmlElement(name = "custom_action")
    @XmlJavaTypeAdapter(MapStringAdapter::class)
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
