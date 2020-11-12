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
import org.openehr.base.foundationtypes.IntervalOfInteger
import java.io.Serializable

/**
 * @author Primoz Delopst
 */
class TaskRepeat() : RmObject(), Serializable {

    var repeats: IntervalOfInteger? = null
    var terminateCondition: PlanEvent? = null
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