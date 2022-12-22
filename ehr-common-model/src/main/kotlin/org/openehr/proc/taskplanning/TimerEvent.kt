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
import care.better.platform.annotation.Required
import org.openehr.rm.datastructures.ItemStructure
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlType

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TIMER_EVENT", propOrder = ["duration", "purpose"])
@Open
class TimerEvent : PlanEvent {
    companion object {
        private const val serialVersionUID: Long = 0L
    }

    @XmlElement(required = true)
    @Required
    var duration: String? = null

    var purpose: String? = null

    constructor()

    constructor(duration: String?) {
        this.duration = duration
    }

    constructor(otherDetails: ItemStructure?, duration: String?, purpose: String?) : super(otherDetails) {
        this.duration = duration
        this.purpose = purpose
    }

    override fun toString(): String =
        "TimerEvent{" +
                "duration=$duration" +
                ", purpose='$purpose'" +
                "} ${super.toString()}"
}
