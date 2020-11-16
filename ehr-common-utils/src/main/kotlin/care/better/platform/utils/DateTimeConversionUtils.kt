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

package care.better.platform.utils

import org.joda.time.DateTime
import org.joda.time.Period
import java.time.*
import java.time.temporal.ChronoUnit
import java.util.*
import java.util.regex.Pattern

/**
 * @author Bostjan Lah
 * @author Primoz Delopst
 */

@Suppress("MemberVisibilityCanBePrivate", "unused", "DuplicatedCode")
class DateTimeConversionUtils {
    companion object {
        private val ZONED_DATE_TIME_PARSER = Pattern.compile("^([-+]?[0-9]{4})(?:-?([0-9]{2})(?:-?([0-9]{2})(?:T([0-9]{2})(?::?([0-9]{2})(?::?([0-9]{2})(?:[.,]([0-9]{1,9}))?)?)?(?:([+-][0-9]{2})(?::?([0-9]{2})(?::?([0-9]{2}))?)?|(Z))?)?)?)?$")
        private val LOCAL_DATE_PARSER = Pattern.compile("^([-+]?[0-9]{4})(?:-?([0-9]{2})(?:-?([0-9]{2}))?)?.*")
        private val LOCAL_TIME_PARSER = Pattern.compile("^([0-9]{2})(?::?([0-9]{2})(?::?([0-9]{2})(?:[.,]([0-9]{1,9}))?)?)?.*")
        private val OFFSET_TIME_PARSER = Pattern.compile("^([0-9]{2})(?::?([0-9]{2})(?::?([0-9]{2})(?:[.,]([0-9]{1,9}))?)?)?(?:([+-][0-9]{2})(?::?([0-9]{2})(?::?([0-9]{2}))?)?|(Z))?.*")
        private val FULL_DATE_PATTERN = Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}.*")
        private val FULL_DATE_TIME_PATTERN = Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}.*")


        /**
         * This method parses string in valid openEHR datetime format to a ZonedDateTime.
         * It allows datetimes in regular (YYYYMMdd) or extended (YYYY-MM-dd), decimal comma or dot
         * as fractional separator for sub-second times with a precision down to a nano-second.
         * It also allows parsing openEHR partial datetimes where all fields up to the month may
         * be omitted.
         *
         *
         * Usually the string values would come from DvDateTime.
         *
         * @param value [String] value of datetime
         * @return [ZonedDateTime]
         * @throws [DateTimeException] when string could not be parsed
         */
        fun toZonedDateTime(value: String): ZonedDateTime = toZonedDateTime(value, false)

        /**
         * This method parses string in valid openEHR datetime format to a ZonedDateTime.
         * It allows datetimes in regular (YYYYMMdd) or extended (YYYY-MM-dd), decimal comma or dot
         * as fractional separator for sub-second times with a precision down to a nano-second.
         * It also allows parsing openEHR partial datetimes where all fields up to the month may
         * be omitted.
         *
         *
         * Usually the string values would come from DvDateTime.
         *
         * @param value  [String] value of datetime
         * @param strict throw an exception when date is not complete (date, time, tz)
         * @return [ZonedDateTime]
         * @throws [DateTimeException] when string could not be parsed
         */
        fun toZonedDateTime(value: String, strict: Boolean): ZonedDateTime =
                with(ZONED_DATE_TIME_PARSER.matcher(value)) {
                    if (this.matches()) {
                        return try {
                            val localDateTime = LocalDateTime.of(
                                    LocalDate.of(
                                            this.group(1).toInt(),
                                            parseInt(this.group(2), strict, 1),
                                            parseInt(this.group(3), strict, 1)),
                                    LocalTime.of(
                                            parseInt(this.group(4), strict, 0),
                                            parseInt(this.group(5), strict, 0),
                                            parseInt(this.group(6), false, 0),
                                            if (this.group(7) == null)
                                                0
                                            else
                                                parseInt((this.group(7) + "00000000").substring(0, 9), strict, 0)))

                            val zone: ZoneId = when {
                                this.group(8) != null ->
                                    ZoneOffset.ofHoursMinutesSeconds(
                                            parseInt(this.group(8), strict, 0),
                                            parseInt(this.group(9), strict, 0),
                                            parseInt(this.group(10), false, 0))
                                this.group(11) != null -> ZoneOffset.UTC
                                else -> ZoneId.systemDefault()
                            }
                            ZonedDateTime.of(localDateTime, zone)
                        } catch (e: NumberFormatException) {
                            throw DateTimeException("Invalid date time value: $value", e)
                        }
                    }
                    throw DateTimeException("Invalid date time value: $value")
                }

        private fun parseInt(`val`: String?, strict: Boolean, defaultValue: Int): Int =
                try {
                    `val`?.toInt() ?: throw NumberFormatException()
                } catch (e: NumberFormatException) {
                    if (strict) {
                        throw e
                    }
                    defaultValue
                }

        /**
         * This method parses string in valid openEHR datetime format to a OffsetDateTime.
         * It allows datetimes in regular (YYYYMMdd) or extended (YYYY-MM-dd), decimal comma or dot
         * as fractional separator for sub-second times with a precision down to a nano-second.
         * It also allows parsing openEHR partial datetimes where all fields up to the month may
         * be omitted.
         *
         *
         * Usually the string values would come from DvDateTime.
         *
         * @param value [String] value of datetime
         * @return [OffsetDateTime]
         * @throws [DateTimeException] when string could not be parsed
         */
        fun toOffsetDateTime(value: String): OffsetDateTime = toOffsetDateTime(value, false)

        /**
         * This method parses string in valid openEHR datetime format to a OffsetDateTime.
         * It allows datetimes in regular (YYYYMMdd) or extended (YYYY-MM-dd), decimal comma or dot
         * as fractional separator for sub-second times with a precision down to a nano-second.
         * It also allows parsing openEHR partial datetimes where all fields up to the month may
         * be omitted.
         *
         *
         * Usually the string values would come from DvDateTime.
         *
         * @param value  [String] value of datetime
         * @param strict throw an exception when date is not complete (date, time, tz)
         * @return [OffsetDateTime]
         * @throws [DateTimeException] when string could not be parsed
         */
        fun toOffsetDateTime(value: String, strict: Boolean): OffsetDateTime = toZonedDateTime(value, strict).toOffsetDateTime()


        /**
         * This method parses string in valid openEHR datetime format to a LocalDate.
         * It allows datetimes in regular (YYYYMMdd) or extended (YYYY-MM-dd).
         * It also allows parsing openEHR partial dates where all fields up to the year may
         * be omitted.
         *
         *
         * Usually the string values would come from DvDate.
         *
         * @param value [String] value of datetime
         * @return [LocalDate]
         * @throws [DateTimeException] when string could not be parsed
         */
        fun toLocalDate(value: String): LocalDate = toLocalDate(value, false)

        fun toLocalDate(value: String, strict: Boolean): LocalDate =
                with(LOCAL_DATE_PARSER.matcher(value)) {
                    if (this.matches()) {
                        return try {
                            LocalDate.of(
                                    this.group(1).toInt(),
                                    parseInt(this.group(2), strict, 1),
                                    parseInt(this.group(3), strict, 1))
                        } catch (e: NumberFormatException) {
                            throw DateTimeException("Invalid full date value: $value", e)
                        }
                    }
                    throw DateTimeException("Invalid date value: $value")
                }

        /**
         * This method parses string in valid openEHR time format to a LocalTime.
         * It allows times in regular (HHmmss.S) or extended (HH:mm:ss.S).
         *
         *
         * Usually the string values would come from DvTime.
         *
         * @param value [String] value of time
         * @return [LocalTime]
         * @throws [DateTimeException] when string could not be parsed
         */
        fun toLocalTime(value: String): LocalTime =
                with(LOCAL_TIME_PARSER.matcher(normalizeTimeString(value))) {
                    if (this.matches()) {
                        return LocalTime.of(
                                if (this.group(1) == null) 0 else this.group(1).toInt(),
                                if (this.group(2) == null) 0 else this.group(2).toInt(),
                                if (this.group(3) == null) 0 else this.group(3).toInt(),
                                if (this.group(4) == null) 0 else (this.group(4) + "00000000").substring(0, 9).toInt())
                    }
                    throw DateTimeException("Invalid time value: $value")
                }

        /**
         * This method parses string in valid openEHR time format to an OffsetTime.
         * It allows times in regular (HHmmss.S+HHmm) or extended (HH:mm:ss.S+HH:mm).
         *
         *
         * Usually the string values would come from DvTime.
         *
         * @param value [String] value of time
         * @return [OffsetTime]
         * @throws [DateTimeException] when string could not be parsed
         */
        fun toOffsetTime(value: String): OffsetTime = toOffsetTime(value, false)

        /**
         * This method parses string in valid openEHR time format to an OffsetTime.
         * It allows times in regular (HHmmss.S+HHmm) or extended (HH:mm:ss.S+HH:mm).
         *
         *
         * Usually the string values would come from DvTime.
         *
         * @param value  [String] value of time
         * @param strict if true converter will throw an exception when time zone is not in the provided string
         * @return [OffsetTime]
         * @throws [DateTimeException] when string could not be parsed
         */
        fun toOffsetTime(value: String, strict: Boolean): OffsetTime =
                with(OFFSET_TIME_PARSER.matcher(normalizeTimeString(value))) {
                    if (this.matches()) {
                        val localDateTime = LocalTime.of(
                                if (this.group(1) == null) 0 else this.group(1).toInt(),
                                if (this.group(2) == null) 0 else this.group(2).toInt(),
                                if (this.group(3) == null) 0 else this.group(3).toInt(),
                                if (this.group(4) == null) 0 else (this.group(4) + "00000000").substring(0, 9).toInt())

                        val zoneOffset: ZoneOffset = when {
                            this.group(5) != null ->
                                ZoneOffset.ofHoursMinutesSeconds(
                                        if (this.group(5) == null) 0 else this.group(5).toInt(),
                                        if (this.group(6) == null) 0 else this.group(6).toInt(),
                                        if (this.group(7) == null) 0 else this.group(7).toInt())
                            !strict -> ZoneOffset.UTC
                            else -> throw DateTimeException("Invalid time value: $value")
                        }
                        return OffsetTime.of(localDateTime, zoneOffset)
                    }
                    throw DateTimeException("Invalid time value: $value")
                }

        /**
         * This method returns true if supplied string contains a time zone.
         *
         * @param value string value of time
         * @return boolean true when supplied string contains a time-zone
         */
        fun isOffsetTime(value: String): Boolean =
                with(OFFSET_TIME_PARSER.matcher(normalizeTimeString(value))) {
                    if (this.matches()) {
                        if (this.group(5) != null) {
                            this.group(5)?.also { this.group(5).toInt() }
                            this.group(6)?.also { this.group(6).toInt() }
                            this.group(7)?.also { this.group(7).toInt() }
                            return true
                        }
                    }
                    return false
                }

        fun toJodaDateTime(offsetDateTime: OffsetDateTime): DateTime = DateTime(GregorianCalendar.from(offsetDateTime.toZonedDateTime()))

        fun toOffsetDateTime(dateTime: DateTime): OffsetDateTime = dateTime.toGregorianCalendar().toZonedDateTime().toOffsetDateTime()

        fun plusPeriod(offsetDateTime: OffsetDateTime, period: Period): OffsetDateTime =
                offsetDateTime
                        .plus(period.years.toLong(), ChronoUnit.YEARS)
                        .plus(period.months.toLong(), ChronoUnit.MONTHS)
                        .plus(period.weeks.toLong(), ChronoUnit.WEEKS)
                        .plus(period.days.toLong(), ChronoUnit.DAYS)
                        .plus(period.hours.toLong(), ChronoUnit.HOURS)
                        .plus(period.minutes.toLong(), ChronoUnit.MINUTES)
                        .plus(period.seconds.toLong(), ChronoUnit.SECONDS)
                        .plus(period.millis.toLong(), ChronoUnit.MILLIS)


        fun minusPeriod(offsetDateTime: OffsetDateTime, period: Period): OffsetDateTime =
                offsetDateTime
                        .minus(period.years.toLong(), ChronoUnit.YEARS)
                        .minus(period.months.toLong(), ChronoUnit.MONTHS)
                        .minus(period.weeks.toLong(), ChronoUnit.WEEKS)
                        .minus(period.days.toLong(), ChronoUnit.DAYS)
                        .minus(period.hours.toLong(), ChronoUnit.HOURS)
                        .minus(period.minutes.toLong(), ChronoUnit.MINUTES)
                        .minus(period.seconds.toLong(), ChronoUnit.SECONDS)
                        .minus(period.millis.toLong(), ChronoUnit.MILLIS)


        /**
         * Returns true if date is partial (missing days or days and months)
         *
         * @param value string date value
         * @return true if partial, false otherwise
         */
        fun isPartialDate(value: String): Boolean = !FULL_DATE_PATTERN.matcher(value).matches()

        /**
         * Returns true if datetime is partial (missing time, time and days or time, days and months)
         *
         * @param value string datetime value
         * @return true if partial, false otherwise
         */
        fun isPartialDateTime(value: String): Boolean = !FULL_DATE_TIME_PATTERN.matcher(value).matches()

        private fun normalizeTimeString(value: String): String =
                with(value.indexOf('T')) {
                    if (this > 0) value.substring(this + 1) else value
                }
    }
}