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
import org.openehr.base.foundationtypes.IntervalOfInteger
import java.io.Serializable
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlType

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */
@XmlType(name = "TASK_REPEAT", propOrder = [
    "repeats",
    "terminateCondition",
    "period"])
@Open
class TaskRepeat() : RmObject(), Serializable {
    companion object {
        private const val serialVersionUID: Long = 0L
    }

    @XmlElement(name = "repeats")
    var repeats: IntervalOfInteger? = null

    @XmlElement(name = "terminate_condition")
    var terminateCondition: PlanEvent? = null

    @XmlElement(name = "period")
    var period: String? = null

    constructor(terminateCondition: PlanEvent?) : this() {
        this.terminateCondition = terminateCondition
    }

    constructor(repeats: IntervalOfInteger?, terminateCondition: PlanEvent?) : this(terminateCondition) {
        this.repeats = repeats
    }

    override fun toString(): String =
        "TaskRepeat{" +
                "repeats=${intervalToStr(repeats)}" +
                ", terminateCondition=$terminateCondition" +
                ", period=$period" +
                '}'

    private fun intervalToStr(repeats: IntervalOfInteger?): String? =
        repeats?.let {
            (if (it.lowerIncluded == true) '[' else '(') +
                    (if (it.lowerUnbounded) "*" else java.lang.String.valueOf(it.lower)) +
                    ".." +
                    (if (it.upperUnbounded) "*" else java.lang.String.valueOf(it.upper)) +
                    (if (it.upperIncluded == true) ']' else ')')
        }
}
