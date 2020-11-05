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
import org.openehr.rm.common.Locatable
import org.openehr.rm.datastructures.ItemStructure
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlSeeAlso
import javax.xml.bind.annotation.XmlType

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */
@XmlType(name = "PLAN_EVENT", propOrder = ["delay", "otherDetails"])
@XmlSeeAlso(value = [
    ManualNotification::class,
    SystemNotification::class,
    CallbackNotification::class,
    StateTrigger::class,
    TimerEvent::class,
    CalendarEvent::class,
    TimelineMoment::class])
@Open
abstract class PlanEvent constructor() : Locatable() {
    companion object {
        private const val serialVersionUID: Long = 0L
    }

    @XmlElement(name = "other_details")
    var otherDetails: ItemStructure? = null

    @XmlElement
    var delay: String? = null

    protected constructor(otherDetails: ItemStructure?) : this() {
        this.otherDetails = otherDetails
    }

    override fun toString(): String =
        "PlanEvent{" +
                "otherDetails=$otherDetails" +
                ", delay='$delay" +
                ", uid=$uid" +
                '}'
}
