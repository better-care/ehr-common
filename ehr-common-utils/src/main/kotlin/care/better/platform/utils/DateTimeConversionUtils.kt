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

@file:JvmName("DateTimeConversionUtils")

package care.better.platform.utils

import care.better.platform.time.format.DateTimeFormatters
import org.joda.time.DateTime
import org.joda.time.Period
import java.time.*
import java.time.format.DateTimeParseException
import java.time.temporal.ChronoUnit
import java.util.*
import java.util.regex.Pattern

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */

@Suppress("MemberVisibilityCanBePrivate", "unused", "DuplicatedCode")
class DateTimeConversionUtils {
    companion object {

        private val FULL_DATE_PATTERN = Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}.*")
        private val FULL_DATE_TIME_PATTERN = Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}.*")

        /**
         * Parses a date in openEHR format without an offset.
         * This returns a formatter capable of formatting and parsing the regular (YYYYMMdd) or extended (YYYY-MM-dd) date format.
         * It also allows parsing openEHR partial dates where all fields up to the year may be omitted. The year may be omitted.
         *
         * @param value [String] value of date
         * @param strict [Boolean] indicating if value can be parsed as partial date
         * @return [LocalDate]
         * @throws [DateTimeException] when string could not be parsed
         */
        @JvmStatic
        @JvmOverloads
        fun toLocalDate(value: String, strict: Boolean = false): LocalDate =
            if (strict) {
                DateTimeFormatters.PARTIAL_ZONE_DATE_TIME_WITH_STRICT_DATE_FORMATTER
                    .parseBest(
                        value,
                        { ZonedDateTime.from(it) },
                        { OffsetDateTime.from(it) },
                        { LocalDateTime.from(it) },
                        { LocalDate.from(it) })
            } else {
                DateTimeFormatters.PARTIAL_ZONE_DATE_TIME_FORMATTER
                    .parseBest(
                        value,
                        { ZonedDateTime.from(it) },
                        { OffsetDateTime.from(it) },
                        { LocalDateTime.from(it) },
                        { LocalDate.from(it) })
            }.let {
                when (it) {
                    is ZonedDateTime -> it.toLocalDate()
                    is OffsetDateTime -> it.toLocalDate()
                    is LocalDateTime -> it.toLocalDate()
                    is LocalDate -> it
                    else -> throw DateTimeException("Invalid local date value: $value")
                }
            }


        /**
         * Parses a time in openEHR format without an offset.
         * This returns a formatter capable of formatting and parsing the regular (HHmmss.S) or extended (HH:mm:ss.S) time format.
         * It also allows parsing openEHR partial times where all fields may be omitted.
         *
         * @param value [String] value of time
         * @param strict [Boolean] indicating if value can be parsed as partial time
         * @return [LocalTime]
         * @throws [DateTimeException] when string could not be parsed
         */
        @JvmStatic
        @JvmOverloads
        fun toLocalTime(value: String, strict: Boolean = false): LocalTime =
            if (strict) {
                try {
                    DateTimeFormatters.STRICT_OFFSET_TIME_FORMATTER.parseBest(value, { OffsetTime.from(it) }, { LocalTime.from(it) })
                } catch (ex: DateTimeParseException) {
                    DateTimeFormatters.PARTIAL_ZONE_DATE_TIME_WITH_STRICT_TIME_FORMATTER
                        .parseBest(
                            value,
                            { ZonedDateTime.from(it) },
                            { OffsetDateTime.from(it) },
                            { LocalDateTime.from(it) },
                            { OffsetTime.from(it) },
                            { LocalTime.from(it) })
                }
            } else {
                try {
                    DateTimeFormatters.PARTIAL_OFFSET_TIME_FORMATTER.parseBest(value, { OffsetTime.from(it) }, { LocalTime.from(it) })
                } catch (ex: DateTimeParseException) {
                    DateTimeFormatters.PARTIAL_ZONE_DATE_TIME_FORMATTER
                        .parseBest(
                            value,
                            { ZonedDateTime.from(it) },
                            { OffsetDateTime.from(it) },
                            { LocalDateTime.from(it) },
                            { OffsetTime.from(it) },
                            { LocalTime.from(it) })
                }
            }.let {
                when (it) {
                    is ZonedDateTime -> it.toLocalTime()
                    is OffsetDateTime -> it.toLocalTime()
                    is LocalDateTime -> it.toLocalTime()
                    is OffsetTime -> it.toLocalTime()
                    is LocalTime -> it
                    else -> throw DateTimeException("Invalid local time value: $value")
                }
            }

        /**
         * Parses a date time in openEHR format without an offset.
         * This returns a formatter capable of formatting and parsing the regular (YYYYMMdd) or extended (YYYY-MM-dd) date format
         * and regular (HHmmss.S) or extended (HH:mm:ss.S) time format.
         * It also allows parsing openEHR partial date times where all fields up to the year may be omitted.
         *
         * @param value [String] value of date time
         * @param strict [Boolean] indicating if value can be parsed as partial date time
         * @return [LocalDateTime]
         * @throws [DateTimeException] when string could not be parsed
         */
        @JvmStatic
        @JvmOverloads
        fun toLocalDateTime(value: String, strict: Boolean = false): LocalDateTime =
            if (strict) {
                DateTimeFormatters.STRICT_ZONE_DATE_TIME_FORMATTER
                    .parseBest(
                        value,
                        { ZonedDateTime.from(it) },
                        { OffsetDateTime.from(it) },
                        { LocalDateTime.from(it) },
                        { LocalDate.from(it) })
            } else {
                DateTimeFormatters.PARTIAL_ZONE_DATE_TIME_FORMATTER
                    .parseBest(
                        value,
                        { ZonedDateTime.from(it) },
                        { OffsetDateTime.from(it) },
                        { LocalDateTime.from(it) },
                        { LocalDate.from(it) })
            }.let {
                when (it) {
                    is ZonedDateTime -> it.toLocalDateTime()
                    is OffsetDateTime -> it.toLocalDateTime()
                    is LocalDateTime -> it
                    is LocalDate -> LocalDateTime.of(it, LocalTime.of(0, 0, 0, 0))
                    else -> throw DateTimeException("Invalid offset date time value: $value")
                }
            }

        /**
         * Parses a time in openEHR format with an offset.
         * This returns a formatter capable of formatting and parsing the regular (HHmmss.S, )
         * or extended (HH:mm:ss.S) time format.
         * It also allows parsing openEHR partial times where all fields may be omitted.
         *
         * @param value [String] value of time
         * @param strict [Boolean] indicating if value can be parsed as partial time
         * @return [OffsetTime]
         * @throws [DateTimeException] when string could not be parsed
         */
        @JvmStatic
        @JvmOverloads
        fun toOffsetTime(value: String, strict: Boolean = false): OffsetTime =
            if (strict) {
                try {
                    DateTimeFormatters.STRICT_OFFSET_TIME_FORMATTER.parseBest(value, { OffsetTime.from(it) }, { LocalTime.from(it) })
                } catch (ex: DateTimeParseException) {
                    DateTimeFormatters.PARTIAL_ZONE_DATE_TIME_WITH_STRICT_TIME_FORMATTER
                        .parseBest(
                            value,
                            { ZonedDateTime.from(it) },
                            { OffsetDateTime.from(it) },
                            { LocalDateTime.from(it) },
                            { OffsetTime.from(it) },
                            { LocalTime.from(it) })
                }
            } else {
                try {
                    DateTimeFormatters.PARTIAL_OFFSET_TIME_FORMATTER.parseBest(value, { OffsetTime.from(it) }, { LocalTime.from(it) })
                } catch (ex: DateTimeParseException) {
                    DateTimeFormatters.PARTIAL_ZONE_DATE_TIME_FORMATTER
                        .parseBest(
                            value,
                            { ZonedDateTime.from(it) },
                            { OffsetDateTime.from(it) },
                            { LocalDateTime.from(it) },
                            { OffsetTime.from(it) },
                            { LocalTime.from(it) })
                }
            }.let {
                when (it) {
                    is ZonedDateTime -> it.toOffsetDateTime().toOffsetTime()
                    is OffsetDateTime -> it.toOffsetTime()
                    is LocalDateTime -> OffsetTime.of(
                        it.toLocalTime(),
                        if (strict)
                            throw DateTimeException("Invalid offset time value: $value")
                        else
                            ZoneOffset.UTC)
                    is OffsetTime -> it
                    is LocalTime -> OffsetTime.of(it, if (strict) throw DateTimeException("Invalid offset time value: $value") else ZoneOffset.UTC)
                    else -> throw DateTimeException("Invalid offset time value: $value")
                }
            }

        /**
         * Parses a date time in openEHR format with an offset.
         * This returns a formatter capable of formatting and parsing the regular (YYYYMMdd) or extended (YYYY-MM-dd) date format,
         * regular (HHmmss.S) or extended (HH:mm:ss.S) time format and offset in +HH, +HHmm, +HH:mm, +HHMM, +HH:MM, +HHMMss, +HH:MM:ss, +HHMMSS, +HH:MM:SS formats.
         * It also allows parsing openEHR partial date times where all fields up to the year may be omitted.
         *
         * @param value [String] value of date time
         * @param strict [Boolean] indicating if value can be parsed as partial date time
         * @return [OffsetDateTime]
         * @throws [DateTimeException] when string could not be parsed
         */
        @JvmStatic
        @JvmOverloads
        fun toOffsetDateTime(value: String, strict: Boolean = false): OffsetDateTime =
            if (strict) {
                DateTimeFormatters.STRICT_ZONE_DATE_TIME_FORMATTER
                    .parseBest(
                        value,
                        { ZonedDateTime.from(it) },
                        { OffsetDateTime.from(it) },
                        { LocalDateTime.from(it) },
                        { LocalDate.from(it) })
            } else {
                DateTimeFormatters.PARTIAL_ZONE_DATE_TIME_FORMATTER
                    .parseBest(
                        value,
                        { ZonedDateTime.from(it) },
                        { OffsetDateTime.from(it) },
                        { LocalDateTime.from(it) },
                        { LocalDate.from(it) })
            }.let {
                when (it) {
                    is ZonedDateTime -> it.toOffsetDateTime()
                    is OffsetDateTime -> it
                    is LocalDateTime -> ZonedDateTime.of(it, ZoneId.systemDefault()).toOffsetDateTime()
                    is LocalDate -> ZonedDateTime.of(LocalDateTime.of(it, LocalTime.of(0, 0, 0, 0)), ZoneId.systemDefault()).toOffsetDateTime()
                    else -> throw DateTimeException("Invalid offset date time value: $value")
                }
            }.plusNanos(500).truncatedTo(ChronoUnit.MICROS)

        /**
         * Parses a date time in openEHR format with an offset and zone.
         * This returns a formatter capable of formatting and parsing the regular (YYYYMMdd) or extended (YYYY-MM-dd) date format,
         * regular (HHmmss.S) or extended (HH:mm:ss.S) time format and offset in +HH, +HHmm, +HH:mm, +HHMM, +HH:MM, +HHMMss, +HH:MM:ss, +HHMMSS, +HH:MM:SS formats.
         * It also allows parsing openEHR partial date times where all fields up to the year may be omitted.
         *
         * @param value [String] value of date time
         * @param strict [Boolean] indicating if value can be parsed as partial date time
         * @return [ZonedDateTime]
         * @throws [DateTimeException] when string could not be parsed
         */
        @JvmStatic
        @JvmOverloads
        fun toZonedDateTime(value: String, strict: Boolean = false): ZonedDateTime =
            if (strict) {
                DateTimeFormatters.STRICT_ZONE_DATE_TIME_FORMATTER
                    .parseBest(
                        value,
                        { ZonedDateTime.from(it) },
                        { OffsetDateTime.from(it) },
                        { LocalDateTime.from(it) },
                        { LocalDate.from(it) })
            } else {
                DateTimeFormatters.PARTIAL_ZONE_DATE_TIME_FORMATTER
                    .parseBest(
                        value,
                        { ZonedDateTime.from(it) },
                        { OffsetDateTime.from(it) },
                        { LocalDateTime.from(it) },
                        { LocalDate.from(it) })
            }.let {
                when (it) {
                    is ZonedDateTime -> it
                    is OffsetDateTime -> ZonedDateTime.of(it.toLocalDateTime(), ZoneId.systemDefault())
                    is LocalDateTime -> ZonedDateTime.of(it, ZoneId.systemDefault())
                    is LocalDate -> ZonedDateTime.of(LocalDateTime.of(it, LocalTime.of(0, 0, 0, 0)), ZoneId.systemDefault())
                    else -> throw DateTimeException("Invalid zoned date time value: $value")
                }
            }

        /**
         * This method returns true if supplied string contains a time zone.
         *
         * @param value [String] value of time
         * @return true when supplied string contains a time-zone, otherwise false
         */
        @JvmStatic
        fun isOffsetTime(value: String): Boolean =
            try {
                DateTimeFormatters.PARTIAL_OFFSET_TIME_FORMATTER
                    .parseBest(value, { OffsetTime.from(it) }, { LocalTime.from(it) })
            } catch (ex: DateTimeParseException) {
                DateTimeFormatters.PARTIAL_ZONE_DATE_TIME_FORMATTER
                    .parseBest(
                        value,
                        { ZonedDateTime.from(it) },
                        { OffsetDateTime.from(it) },
                        { LocalDateTime.from(it) },
                        { OffsetTime.from(it) },
                        { LocalTime.from(it) })
            }.let {
                when (it) {
                    is ZonedDateTime -> true
                    is OffsetDateTime -> true
                    is LocalDateTime -> true
                    is OffsetTime -> true
                    is LocalTime -> false
                    else -> throw DateTimeException("Invalid offset time value: $value")
                }
            }

        /**
         * Converts [OffsetDateTime] to [DateTime]
         *
         * @param offsetDateTime [OffsetDateTime]
         * @return [DateTime]
         */
        @JvmStatic
        fun toDateTime(offsetDateTime: OffsetDateTime): DateTime = DateTime(GregorianCalendar.from(offsetDateTime.toZonedDateTime()))

        /**
         * Converts [DateTime] to [OffsetDateTime]
         *
         * @param dateTime [DateTime]
         * @return [OffsetDateTime]
         */
        @JvmStatic
        fun toOffsetDateTime(dateTime: DateTime): OffsetDateTime = dateTime.toGregorianCalendar()
            .toZonedDateTime()
            .toOffsetDateTime()
            .plusNanos(500).truncatedTo(ChronoUnit.MICROS)

        /**
         * Adds [Period] to [OffsetDateTime]
         *
         * @param offsetDateTime [OffsetDateTime]
         * @return [OffsetDateTime]
         */
        @JvmStatic
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


        /**
         * Deducts [Period] from [OffsetDateTime]
         *
         * @param offsetDateTime [OffsetDateTime]
         * @return [OffsetDateTime]
         */
        @JvmStatic
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
         * @param value [String] date value
         * @return true when supplied string contains a time-zone, otherwise false
         */
        @JvmStatic
        fun isPartialDate(value: String): Boolean = !FULL_DATE_PATTERN.matcher(value).matches()

        /**
         * Returns true if datetime is partial (missing time, time and days or time, days and months)
         *
         * @param value string datetime value
         * @return true when supplied string contains a time-zone, otherwise false
         */
        @JvmStatic
        fun isPartialDateTime(value: String): Boolean = !FULL_DATE_TIME_PATTERN.matcher(value).matches()
    }
}
