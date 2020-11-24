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

@file:Suppress("unused")
@file:JvmName("JodaConversionUtils")

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
 */

/**
 * Converts a ReadablePeriod to [DvDuration]
 *
 * @param period [ReadablePeriod]
 * @return [DvDuration] object
 */
@JvmName("createDvDuration")
fun DvDuration.Companion.create(period: ReadablePeriod): DvDuration = DvDuration().apply { this.value = ISOPeriodFormat.standard().print(period) }


/**
 * Converts a string duration to [DvDuration]
 *
 * @param value [String] duration in standard ISO format - PyYmMwWdDThHmMsS.
 * @return [DvDuration] object
 */
@JvmName("createDvDuration")
fun DvDuration.Companion.create(value: String?): DvDuration? =
        value?.let {
            DvDuration().apply {
                ISOPeriodFormat.standard().parsePeriod(value)
                this.value = value
            }
        }

/**
 * Converts [DvDuration] to Joda Period
 *
 * @return [Period]
 */
@JvmName("toPeriod")
fun DvDuration.toPeriod(): Period = ISOPeriodFormat.standard().parsePeriod(requireNotNull(value))


/**
 * Converts duration string value to [Period]
 *
 * @param durationValue duration string
 * @return [Period]
 */
@JvmName("toPeriod")
fun DvDuration.Companion.toPeriod(durationValue: String): Period = ISOPeriodFormat.standard().parsePeriod(durationValue)

/**
 * Converts Joda DateTime to DV_DATETIME
 *
 * @param dateTime Joda DateTime
 * @return DV_DATETIME object
 */
@JvmName("createDvDateTime")
fun DvDateTime.Companion.create(dateTime: DateTime): DvDateTime = DvDateTime().apply { this.value = ISODateTimeFormat.dateTime().print(dateTime) }

/**
 * Converts [LocalDate] to [DvDate]
 *
 * @param date [LocalDate]
 * @return [DvDate] object
 */
@JvmName("createDvDate")
fun DvDate.Companion.create(date: LocalDate): DvDate = DvDate().apply { this.value = ISODateTimeFormat.date().print(date) }

/**
 * Converts [LocalTime] to [DvTime]
 *
 * @param time [LocalTime]
 * @return [DvTime] object
 */
@JvmName("createDvTime")
fun DvTime.Companion.create(time: LocalTime): DvTime = DvTime().apply { this.value = ISODateTimeFormat.time().print(time) }

/**
 * Converts DV_DATETIME to Joda DateTime
 *
 * @return Joda DateTime
 */
@JvmName("toDateTime")
fun DvDateTime.toDateTime(): DateTime = ISODateTimeFormat.dateTimeParser().withOffsetParsed().parseDateTime(requireNotNull(value))

/**
 * Converts string date time to [DateTime]
 *
 * @param dateTimeValue string value (ISO format)
 * @return [DateTime]
 */
@JvmName("toDateTime")
fun DvDateTime.Companion.toDateTime(dateTimeValue: String): DateTime = ISODateTimeFormat.dateTimeParser().withOffsetParsed().parseDateTime(dateTimeValue)

/**
 * Converts [DvTime] to [LocalTime]
 *
 * @return [LocalTime]
 */
@JvmName("toLocalTime")
fun DvTime.toJodaLocalTime(): LocalTime =
        with(requireNotNull(value)) {
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
 * @return [LocalDate]
 */
@JvmName("toLocalDate")
fun DvDate.toJodaLocalDate(): LocalDate = ISODateTimeFormat.dateOptionalTimeParser().withOffsetParsed().parseLocalDate(requireNotNull(value))
