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


package care.better.platform.utils

import org.openehr.rm.datatypes.DvDate
import org.openehr.rm.datatypes.DvDateTime
import org.openehr.rm.datatypes.DvTime
import java.time.*

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */


class JSR310ConversionUtils {

    companion object {
        /**
         * Converts [DvDateTime] to [ZonedDateTime]
         *
         * @param dateTime [DvDateTime]
         * @return [ZonedDateTime]
         */
        @JvmStatic
        fun toZonedDateTime(dateTime: DvDateTime): ZonedDateTime = DateTimeConversionUtils.toZonedDateTime(requireNotNull(dateTime.value))

        /**
         * Converts [DvDateTime] to [OffsetDateTime]
         *
         * @param dateTime [DvDateTime]
         * @return [OffsetDateTime]
         */
        @JvmStatic
        fun toOffsetDateTime(dateTime: DvDateTime): OffsetDateTime = DateTimeConversionUtils.toOffsetDateTime(requireNotNull(dateTime.value))

        /**
         * Converts [DvDateTime] to [LocalDateTime]
         *
         * @param dateTime [DvDateTime]
         * @return [LocalDateTime]
         */
        @JvmStatic
        fun toLocalDateTime(dateTime: DvDateTime): LocalDateTime = DateTimeConversionUtils.toLocalDateTime(requireNotNull(dateTime.value))

        /**
         * Converts [DvDate] to [LocalDate]
         *
         * @param date [DvDate]
         * @return [DvDateTime]
         */
        @JvmStatic
        fun toLocalDate(date: DvDate): LocalDate = DateTimeConversionUtils.toLocalDate(requireNotNull(date.value))

        /**
         * Converts [DvTime] to [LocalTime]
         *
         * @param time [DvTime]
         * @return [LocalTime]
         */
        @JvmStatic
        fun toLocalTime(time: DvTime): LocalTime = DateTimeConversionUtils.toLocalTime(requireNotNull(time.value))

        /**
         * Converts [DvTime] to [OffsetTime]
         *
         * @param time [DvTime]
         * @return [OffsetTime]
         */
        @JvmStatic
        fun toOffsetTime(time: DvTime): OffsetTime = DateTimeConversionUtils.toOffsetTime(requireNotNull(time.value))

    }
}

/**
 * Converts [DvDateTime] to [ZonedDateTime]
 *
 * @return [ZonedDateTime]
 */
@JvmSynthetic
fun DvDateTime.toZonedDateTime(): ZonedDateTime = JSR310ConversionUtils.toZonedDateTime(this)

/**
 * Converts [DvDateTime] to [OffsetDateTime]
 *
 * @return [OffsetDateTime]
 */
@JvmSynthetic
fun DvDateTime.toOffsetDateTime(): OffsetDateTime = JSR310ConversionUtils.toOffsetDateTime(this)


/**
 * Converts [DvDateTime] to [LocalDateTime]
 *
 * @return [LocalDateTime]
 */
@JvmSynthetic
fun DvDateTime.toLocalDateTime(): LocalDateTime = JSR310ConversionUtils.toLocalDateTime(this)

/**
 * Converts [DvDate] to [LocalDate]
 *
 * @return [DvDateTime]
 */
@JvmSynthetic
fun DvDate.toLocalDate(): LocalDate = JSR310ConversionUtils.toLocalDate(this)

/**
 * Converts [DvTime] to [LocalTime]
 *
 * @return [LocalTime]
 */
@JvmSynthetic
fun DvTime.toLocalTime(): LocalTime = JSR310ConversionUtils.toLocalTime(this)

/**
 * Converts [DvTime] to [OffsetTime]
 *
 * @return [OffsetTime]
 */
@JvmSynthetic
fun DvTime.toOffsetTime(): OffsetTime = JSR310ConversionUtils.toOffsetTime(this)
