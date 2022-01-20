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

@file:Suppress("unused")

package care.better.platform.utils

import org.joda.time.*
import org.joda.time.format.ISODateTimeFormat
import org.joda.time.format.ISOPeriodFormat
import org.openehr.rm.datatypes.DvDate
import org.openehr.rm.datatypes.DvDateTime
import org.openehr.rm.datatypes.DvDuration
import org.openehr.rm.datatypes.DvTime


/**
 * @author Primoz Delopst
 * @since 3.1.0
 */


class JodaConversionUtils {
    companion object {

        private val STANDARD_PERIOD_FORMATTER = ISOPeriodFormat.standard()

        /**
         * Converts a ReadablePeriod to [DvDuration]
         *
         * @param period [ReadablePeriod]
         * @return [DvDuration] object
         */
        @JvmStatic
        fun createDvDuration(period: ReadablePeriod): DvDuration = DvDuration().apply { this.value = STANDARD_PERIOD_FORMATTER.print(period) }

        /**
         * Converts a string duration to [DvDuration]
         *
         * @param value [String] duration in standard ISO format - PyYmMwWdDThHmMsS.
         * @return [DvDuration] object
         */
        @JvmStatic
        fun createDvDuration(value: String?): DvDuration? =
            value?.let {
                DvDuration().apply {
                    toPeriod(value)
                    this.value = value
                }
            }

        /**
         * Converts duration string value to [Period]
         *
         * @param durationValue duration string
         * @return [Period]
         */
        @JvmStatic
        fun toPeriod(durationValue: String): Period {
            val (value, negative) =
                if (durationValue.startsWith("-P")) {
                    Pair(durationValue.substring(1), true)
                } else {
                    Pair(durationValue, false)
                }
            val period = STANDARD_PERIOD_FORMATTER.parsePeriod(value)

            return if (negative)
                period.negated()
            else
                period
        }

        /**
         * Converts Joda DateTime to DV_DATETIME
         *
         * @param dateTime Joda DateTime
         * @return DV_DATETIME object
         */
        @JvmStatic
        fun createDvDateTime(dateTime: DateTime): DvDateTime = DvDateTime().apply { this.value = ISODateTimeFormat.dateTime().print(dateTime) }

        /**
         * Converts [LocalDate] to [DvDate]
         *
         * @param date [LocalDate]
         * @return [DvDate] object
         */
        @JvmStatic
        fun createDvDate(date: LocalDate): DvDate = DvDate().apply { this.value = ISODateTimeFormat.date().print(date) }

        /**
         * Converts [LocalTime] to [DvTime]
         *
         * @param time [LocalTime]
         * @return [DvTime] object
         */
        @JvmStatic
        fun createDvTime(time: LocalTime): DvTime = DvTime().apply { this.value = ISODateTimeFormat.time().print(time) }

        /**
         * Converts string date time to [DateTime]
         *
         * @param dateTimeValue string value (ISO format)
         * @return [DateTime]
         */
        @JvmStatic
        fun toDateTime(dateTimeValue: String): DateTime = ISODateTimeFormat.dateTimeParser().withOffsetParsed().parseDateTime(dateTimeValue)


        /**
         * Converts [DvTime] to [LocalTime]
         *
         * @param time [DvTime]
         * @return [LocalTime]
         */
        @JvmStatic
        fun toLocalTime(time: DvTime): LocalTime =
            with(requireNotNull(time.value)) {
                val timeIndex = this.indexOf('T')
                ISODateTimeFormat.timeParser().withOffsetParsed().parseLocalTime(
                    if (timeIndex == -1)
                        this
                    else
                        this.substring(timeIndex + 1))
            }

        /**
         * Converts [DvDate] to [LocalDate]
         *
         * @param date [DvDate]
         * @return [LocalDate]
         */
        @JvmStatic
        fun toLocalDate(date: DvDate): LocalDate = ISODateTimeFormat.dateOptionalTimeParser().withOffsetParsed().parseLocalDate(requireNotNull(date.value))

        /**
         * Converts DV_DATETIME to Joda DateTime
         *
         * @param dateTime [DvDateTime]
         * @return Joda DateTime
         */
        @JvmStatic
        fun toDateTime(dateTime: DvDateTime): DateTime = ISODateTimeFormat.dateTimeParser().withOffsetParsed().parseDateTime(requireNotNull(dateTime.value))

        /**
         * Converts [DvDuration] to Joda Period
         *
         * @param duration [DvDuration]
         * @return [Period]
         */
        @JvmStatic
        fun toPeriod(duration: DvDuration): Period = toPeriod(requireNotNull(duration.value))
    }
}

/**
 * Converts a ReadablePeriod to [DvDuration]
 *
 * @param period [ReadablePeriod]
 * @return [DvDuration] object
 */
@JvmSynthetic
fun DvDuration.Companion.create(period: ReadablePeriod): DvDuration = JodaConversionUtils.createDvDuration(period)

/**
 * Converts a string duration to [DvDuration]
 *
 * @param value [String] duration in standard ISO format - PyYmMwWdDThHmMsS.
 * @return [DvDuration] object
 */
@JvmSynthetic
fun DvDuration.Companion.create(value: String?): DvDuration? = JodaConversionUtils.createDvDuration(value)


/**
 * Converts duration string value to [Period]
 *
 * @param durationValue duration string
 * @return [Period]
 */
@JvmSynthetic
fun DvDuration.Companion.toPeriod(durationValue: String): Period = JodaConversionUtils.toPeriod(durationValue)

/**
 * Converts Joda DateTime to DV_DATETIME
 *
 * @param dateTime Joda DateTime
 * @return DV_DATETIME object
 */
@JvmSynthetic
fun DvDateTime.Companion.create(dateTime: DateTime): DvDateTime = JodaConversionUtils.createDvDateTime(dateTime)

/**
 * Converts [LocalDate] to [DvDate]
 *
 * @param date [LocalDate]
 * @return [DvDate] object
 */
@JvmSynthetic
fun DvDate.Companion.create(date: LocalDate): DvDate = JodaConversionUtils.createDvDate(date)

/**
 * Converts [LocalTime] to [DvTime]
 *
 * @param time [LocalTime]
 * @return [DvTime] object
 */
@JvmSynthetic
fun DvTime.Companion.create(time: LocalTime): DvTime = JodaConversionUtils.createDvTime(time)


/**
 * Converts string date time to [DateTime]
 *
 * @param dateTimeValue string value (ISO format)
 * @return [DateTime]
 */
@JvmSynthetic
fun DvDateTime.Companion.toDateTime(dateTimeValue: String): DateTime = JodaConversionUtils.toDateTime(dateTimeValue)

/**
 * Converts [DvDuration] to Joda Period
 *
 * @return [Period]
 */
@JvmSynthetic
fun DvDuration.toPeriod(): Period = JodaConversionUtils.toPeriod(this)

/**
 * Converts DV_DATETIME to Joda DateTime
 *
 * @return Joda DateTime
 */
@JvmSynthetic
fun DvDateTime.toDateTime(): DateTime = JodaConversionUtils.toDateTime(this)

/**
 * Converts [DvTime] to [LocalTime]
 *
 * @return [LocalTime]
 */
@JvmSynthetic
fun DvTime.toJodaLocalTime(): LocalTime = JodaConversionUtils.toLocalTime(this)

/**
 * Converts [DvDate] to [LocalDate]
 *
 * @return [LocalDate]
 */
@JvmSynthetic
fun DvDate.toJodaLocalDate(): LocalDate = JodaConversionUtils.toLocalDate(this)
