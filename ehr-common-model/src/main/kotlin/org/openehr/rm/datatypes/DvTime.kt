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

package org.openehr.rm.datatypes

import care.better.platform.annotation.Required
import java.time.LocalTime
import java.time.OffsetTime
import java.time.format.DateTimeFormatter

/**
 * @author Primoz Delopst
 */

class DvTime : DvTemporal() {

    companion object {
        /**
         * Converts [LocalTime] to [DvTime]
         *
         * @param time [LocalTime]
         * @return [DvTime]
         */
        @JvmStatic
        fun toDvTime(time: LocalTime): DvTime =
                DvTime().apply {
                    this.value = DateTimeFormatter.ISO_LOCAL_TIME.format(time)
                }

        /**
         * Converts [OffsetTime] to [DvTime]
         *
         * @param time [OffsetTime]
         * @return [DvTime]
         */
        @JvmStatic
        fun toDvTime(time: OffsetTime): DvTime =
                DvTime().apply {
                    this.value = DateTimeFormatter.ISO_OFFSET_TIME.format(time)
                }
    }

    @Required
    var value: String? = null
}