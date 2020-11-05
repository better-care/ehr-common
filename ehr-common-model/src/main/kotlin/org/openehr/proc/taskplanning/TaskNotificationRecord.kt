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
import care.better.platform.jaxb.MapStringAdapter
import org.openehr.rm.common.PartyProxy
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlType
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */
@XmlType(
    name = "TASK_NOTIFICATION_RECORD", propOrder = [
        "receiver",
        "receiverTaskPlan",
        "details"])
@Open
class TaskNotificationRecord() : RmObject() {
    companion object {
        private const val serialVersionUID: Long = 0L
    }

    @XmlElement(required = true)
    @Required
    var receiver: PartyProxy? = null

    @XmlElement(name = "receiver_task_plan", required = true)
    @Required
    var receiverTaskPlan: String? = null

    @XmlJavaTypeAdapter(MapStringAdapter::class)
    var details: LinkedHashMap<String, String> = LinkedHashMap()

    constructor(receiver: PartyProxy?, receiverTaskPlan: String?) : this() {
        this.receiver = receiver
        this.receiverTaskPlan = receiverTaskPlan
    }

    override fun toString(): String =
        "TaskNotificationRecord{" +
                "receiver= $receiver" +
                ", receiverTaskPlan='$receiverTaskPlan'" +
                ", details=$details" +
                '}'
}
