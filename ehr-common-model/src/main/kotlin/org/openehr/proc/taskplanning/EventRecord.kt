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
@SuppressWarnings("ClassReferencesSubclass")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EVENT_RECORD", propOrder = ["time", "description"])
@XmlSeeAlso(value = [TaskPlanEventRecord::class, TaskEventRecord::class])
@Open
abstract class EventRecord() : RmObject(), Serializable {
    companion object {
        private const val serialVersionUID: Long = 0L
    }

    @XmlElement(required = true)
    @Required
    var time: String? = null

    @XmlElement
    var description: String? = null

    constructor(time: String?) : this() {
        this.time = time
    }

    constructor(time: String?, description: String?) : this(time) {
        this.description = description
    }

    override fun toString(): String =
        "EventRecord{" +
                "time=$time" +
                ", description='$description'" +
                '}'
}
