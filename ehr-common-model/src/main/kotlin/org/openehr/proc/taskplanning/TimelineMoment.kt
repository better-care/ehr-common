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

import care.better.platform.annotation.Opened
import org.openehr.rm.datastructures.ItemStructure

/**
 * @author Primoz Delopst
 */

@Opened
class TimelineMoment : PlanEvent {

    var timelineOffset: String? = null
    var fixedTime: TimeSpecifier? = null
    var timelineOrigin: PlanTimeOrigin? = null

    constructor()

    constructor(timelineOffset: String?, fixedTime: TimeSpecifier?) : this(null, timelineOffset, fixedTime)

    constructor(otherDetails: ItemStructure?, timelineOffset: String?, fixedTime: TimeSpecifier?) : super(otherDetails) {
        this.timelineOffset = timelineOffset
        this.fixedTime = fixedTime
    }

    override fun toString(): String =
            "TimelineMoment{" +
                    "timelineOffset='$timelineOffset'" +
                    ", fixedTime=$fixedTime" +
                    ", timelineOrigin=$timelineOffset" +
                    "} ${super.toString()}"
}
