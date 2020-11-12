/* Copyright 2020-2025 Better Ltd (www.better.care)
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
import java.io.Serializable

/**
 * @author Primoz Delopst
 */
class TaskWait() : RmObject(), Serializable {
    var events: MutableList<PlanEvent> = mutableListOf()
    var eventRelation: TemporalRelation? = null
    var timeout: TimerWait? = null
    var nextState: TaskLifecycle? = null

    constructor(eventRelation: TemporalRelation?, timeout: TimerWait) : this() {
        this.eventRelation = eventRelation
        this.timeout = timeout
    }

    fun addEvent(event: PlanEvent): TaskWait = events.add(event).let { this }

    override fun toString(): String =
            "TaskWait{" +
                    "events=$events" +
                    ", eventRelation=$eventRelation" +
                    ", timeout=$timeout" +
                    ", nextState=$nextState" +
                    '}'
}