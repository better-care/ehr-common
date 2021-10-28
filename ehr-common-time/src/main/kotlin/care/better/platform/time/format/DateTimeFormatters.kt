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

@file:JvmName("DateTimeFormatters")

package care.better.platform.time.format

import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.format.DecimalStyle
import java.time.format.SignStyle
import java.time.temporal.ChronoField

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */
class DateTimeFormatters {

    companion object {

        /**
         * The date formatter that formats or parses a date in openEHR format without an offset.
         * This returns a formatter capable of formatting and parsing the regular (YYYYMMdd) or extended (YYYY-MM-dd) date format.
         * It also allows parsing openEHR partial dates where all fields up to the year may be omitted.
         */
        @JvmField
        val PARTIAL_DATE_FORMATTER: DateTimeFormatter = DateTimeFormatterBuilder()
            .appendValue(ChronoField.YEAR, 4, 4, SignStyle.EXCEEDS_PAD)
            .appendOptional(DateTimeFormatterBuilder().appendLiteral("-").toFormatter())
            .appendOptional(DateTimeFormatterBuilder().appendValue(ChronoField.MONTH_OF_YEAR, 1, 2, SignStyle.NOT_NEGATIVE).toFormatter())
            .appendOptional(DateTimeFormatterBuilder().appendLiteral("-").toFormatter())
            .appendOptional(DateTimeFormatterBuilder().appendValue(ChronoField.DAY_OF_MONTH, 1, 2, SignStyle.NOT_NEGATIVE).toFormatter())
            .parseDefaulting(ChronoField.MONTH_OF_YEAR, 1)
            .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
            .toFormatter()

        /**
         * The date formatter that formats or parses a date in openEHR format without an offset.
         * This returns a formatter capable of formatting and parsing the regular (YYYYMMdd) or extended (YYYY-MM-dd) date format.
         */
        @JvmField
        val STRICT_DATE_FORMATTER: DateTimeFormatter = DateTimeFormatterBuilder()
            .appendValue(ChronoField.YEAR, 4, 4, SignStyle.EXCEEDS_PAD)
            .appendOptional(DateTimeFormatterBuilder().appendLiteral("-").toFormatter())
            .appendValue(ChronoField.MONTH_OF_YEAR, 1, 2, SignStyle.NOT_NEGATIVE)
            .appendOptional(DateTimeFormatterBuilder().appendLiteral("-").toFormatter())
            .appendValue(ChronoField.DAY_OF_MONTH, 1, 2, SignStyle.NOT_NEGATIVE)
            .toFormatter()

        /**
         * The time formatter that formats or parses a time in openEHR format without an offset.
         * This returns a formatter capable of formatting and parsing the regular (HHmmss.S) or extended (HH:mm:ss.S) time format.
         * It also allows parsing openEHR partial times where all fields may be omitted.
         */
        @JvmField
        val PARTIAL_TIME_FORMATTER: DateTimeFormatter = DateTimeFormatterBuilder()
            .appendOptional(DateTimeFormatterBuilder().appendValue(ChronoField.HOUR_OF_DAY, 1, 2, SignStyle.NOT_NEGATIVE).toFormatter())
            .appendOptional(DateTimeFormatterBuilder().appendLiteral(':').toFormatter())
            .appendOptional(DateTimeFormatterBuilder().appendValue(ChronoField.MINUTE_OF_HOUR, 1, 2, SignStyle.NOT_NEGATIVE).toFormatter())
            .appendOptional(DateTimeFormatterBuilder().appendLiteral(':').toFormatter())
            .appendOptional(DateTimeFormatterBuilder().appendValue(ChronoField.SECOND_OF_MINUTE, 1, 2, SignStyle.NOT_NEGATIVE).toFormatter())
            .appendOptional(DateTimeFormatterBuilder().appendLiteral(",").appendFraction(ChronoField.NANO_OF_SECOND, 0, 9, false).toFormatter())
            .appendOptional(DateTimeFormatterBuilder().appendLiteral(".").appendFraction(ChronoField.NANO_OF_SECOND, 0, 9, false).toFormatter())
            .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
            .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
            .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
            .parseDefaulting(ChronoField.NANO_OF_SECOND, 0)
            .toFormatter()

        /**
         * The time formatter that formats or parses a time in openEHR format without an offset.
         * This returns a formatter capable of formatting and parsing the regular (HHmmss.S) or extended (HH:mm:ss.S) time format.
         */
        @JvmField
        val STRICT_TIME_FORMATTER: DateTimeFormatter = DateTimeFormatterBuilder()
            .appendValue(ChronoField.HOUR_OF_DAY, 1, 2, SignStyle.NOT_NEGATIVE)
            .appendOptional(DateTimeFormatterBuilder().appendLiteral(':').toFormatter())
            .appendValue(ChronoField.MINUTE_OF_HOUR, 1, 2, SignStyle.NOT_NEGATIVE)
            .appendOptional(DateTimeFormatterBuilder().appendLiteral(':').toFormatter())
            .appendOptional(DateTimeFormatterBuilder().appendValue(ChronoField.SECOND_OF_MINUTE, 1, 2, SignStyle.NOT_NEGATIVE).toFormatter())
            .appendOptional(DateTimeFormatterBuilder().appendLiteral(",").appendFraction(ChronoField.NANO_OF_SECOND, 0, 9, false).toFormatter())
            .appendOptional(DateTimeFormatterBuilder().appendLiteral(".").appendFraction(ChronoField.NANO_OF_SECOND, 0, 9, false).toFormatter())
            .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
            .parseDefaulting(ChronoField.NANO_OF_SECOND, 0)
            .toFormatter()

        /**
         * The date time formatter that formats or parses a date time in openEHR format without an offset.
         * This returns a formatter capable of formatting and parsing the regular (YYYYMMdd) or extended (YYYY-MM-dd) date format
         * and regular (HHmmss.S) or extended (HH:mm:ss.S) time format.
         * It also allows parsing openEHR partial date times where all fields up to the year may be omitted.
         */
        @JvmField
        val PARTIAL_LOCAL_DATE_TIME_FORMATTER: DateTimeFormatter = DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .appendOptional(PARTIAL_DATE_FORMATTER)
            .appendOptional(DateTimeFormatterBuilder().appendLiteral("T").append(PARTIAL_TIME_FORMATTER).toFormatter())
            .toFormatter()

        /**
         * The date time formatter that formats or parses a date time in openEHR format without an offset.
         * This returns a formatter capable of formatting and parsing the regular (YYYYMMdd) or extended (YYYY-MM-dd) date format
         * and regular (HHmmss.S) or extended (HH:mm:ss.S) time format.
         */
        @JvmField
        val STRICT_LOCAL_DATE_TIME_FORMATTER: DateTimeFormatter = DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .append(STRICT_DATE_FORMATTER)
            .appendLiteral("T")
            .append(STRICT_TIME_FORMATTER)
            .toFormatter()

        /**
         * The date time formatter that formats or parses an offset.
         * This returns a formatter capable of formatting and parsing offset in +HH, +HHmm, +HH:mm, +HHMM, +HH:MM, +HHMMss, +HH:MM:ss, +HHMMSS, +HH:MM:SS formats.
         */
        @JvmField
        val OFFSET_FORMATTER: DateTimeFormatter = offsetFormatter(strict = false, forFormatting = false)

        /**
         * The date time formatter that formats or parses a zone.
         */
        @JvmField
        val ZONE_FORMATTER: DateTimeFormatter = DateTimeFormatterBuilder()
            .optionalStart()
            .appendLiteral('[')
            .parseCaseSensitive()
            .appendZoneRegionId()
            .appendLiteral(']')
            .toFormatter()

        /**
         * The time formatter that formats or parses a time in openEHR format with an offset.
         * This returns a formatter capable of formatting and parsing the regular (HHmmss.S, )
         * or extended (HH:mm:ss.S) time format.
         * It also allows parsing openEHR partial times where all fields may be omitted.
         */
        @JvmField
        val PARTIAL_OFFSET_TIME_FORMATTER: DateTimeFormatter = DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .appendOptional(PARTIAL_TIME_FORMATTER)
            .appendOptional(OFFSET_FORMATTER)
            .toFormatter()

        /**
         * The time formatter that formats or parses a time in openEHR format with an offset.
         * This returns a formatter capable of formatting and parsing the regular (HHmmss.S) or extended (HH:mm:ss.S) time format.
         */
        @JvmField
        val STRICT_OFFSET_TIME_FORMATTER: DateTimeFormatter = DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .append(STRICT_TIME_FORMATTER)
            .append(OFFSET_FORMATTER)
            .toFormatter()


        /**
         * The date time formatter that formats or parses a date time in openEHR format with an offset.
         * This returns a formatter capable of formatting and parsing the regular (YYYYMMdd) or extended (YYYY-MM-dd) date format,
         * regular (HHmmss.S) or extended (HH:mm:ss.S) time format and offset in +HH, +HHmm, +HH:mm, +HHMM, +HH:MM, +HHMMss, +HH:MM:ss, +HHMMSS, +HH:MM:SS formats.
         * It also allows parsing openEHR partial date times where all fields up to the year may be omitted.
         */
        @JvmField
        val PARTIAL_OFFSET_DATE_TIME_FORMATTER: DateTimeFormatter = DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .appendOptional(PARTIAL_LOCAL_DATE_TIME_FORMATTER)
            .appendOptional(OFFSET_FORMATTER)
            .toFormatter()

        /**
         * The date time formatter that formats or parses a date time in openEHR format with an offset.
         * This returns a formatter capable of formatting and parsing the regular (YYYYMMdd) or extended (YYYY-MM-dd) date format,
         * regular (HHmmss.S) or extended (HH:mm:ss.S) time format and offset in +HH, +HHmm, +HH:mm, +HHMM, +HH:MM, +HHMMss, +HH:MM:ss, +HHMMSS, +HH:MM:SS formats.
         */
        @JvmField
        val STRICT_OFFSET_DATE_TIME_FORMATTER: DateTimeFormatter = DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .append(STRICT_LOCAL_DATE_TIME_FORMATTER)
            .append(OFFSET_FORMATTER)
            .toFormatter()


        /**
         * The date time formatter that formats or parses a date time in openEHR format with an offset and zone.
         * This returns a formatter capable of formatting and parsing the regular (YYYYMMdd) or extended (YYYY-MM-dd) date format,
         * regular (HHmmss.S) or extended (HH:mm:ss.S) time format and offset in +HH, +HHmm, +HH:mm, +HHMM, +HH:MM, +HHMMss, +HH:MM:ss, +HHMMSS, +HH:MM:SS formats.
         * It also allows parsing openEHR partial date times where all fields up to the year may be omitted.
         */
        @JvmField
        val PARTIAL_ZONE_DATE_TIME_FORMATTER: DateTimeFormatter = DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .appendOptional(PARTIAL_OFFSET_DATE_TIME_FORMATTER)
            .appendOptional(ZONE_FORMATTER)
            .toFormatter()

        /**
         * The date time formatter that formats or parses a date time in openEHR format with an offset and zone.
         * This returns a formatter capable of formatting and parsing the regular (YYYYMMdd) or extended (YYYY-MM-dd) date format,
         * regular (HHmmss.S) or extended (HH:mm:ss.S) time format and offset in +HH, +HHmm, +HH:mm, +HHMM, +HH:MM, +HHMMss, +HH:MM:ss, +HHMMSS, +HH:MM:SS formats.
         */
        @JvmField
        val STRICT_ZONE_DATE_TIME_FORMATTER: DateTimeFormatter = DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .append(STRICT_OFFSET_DATE_TIME_FORMATTER)
            .append(ZONE_FORMATTER)
            .toFormatter()

        /**
         * The date time formatter that formats or parses a date time in openEHR format with an offset and zone.
         * This returns a formatter capable of formatting and parsing the regular (YYYYMMdd) or extended (YYYY-MM-dd) date format,
         * regular (HHmmss.S) or extended (HH:mm:ss.S) time format and offset in +HH, +HHmm, +HH:mm, +HHMM, +HH:MM, +HHMMss, +HH:MM:ss, +HHMMSS, +HH:MM:SS formats.
         * It also allows parsing openEHR partial date times where all fields up to the day may be omitted.
         */
        @JvmField
        val PARTIAL_ZONE_DATE_TIME_WITH_STRICT_DATE_FORMATTER: DateTimeFormatter = DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .append(STRICT_DATE_FORMATTER)
            .appendOptional(DateTimeFormatterBuilder().appendLiteral("T").toFormatter())
            .appendOptional(PARTIAL_TIME_FORMATTER)
            .appendOptional(OFFSET_FORMATTER)
            .append(ZONE_FORMATTER)
            .toFormatter()

        /**
         * The date time formatter that formats or parses a date time in openEHR format with an offset and zone.
         * This returns a formatter capable of formatting and parsing the regular (YYYYMMdd) or extended (YYYY-MM-dd) date format,
         * regular (HHmmss.S) or extended (HH:mm:ss.S) time format and offset in +HH, +HHmm, +HH:mm, +HHMM, +HH:MM, +HHMMss, +HH:MM:ss, +HHMMSS, +HH:MM:SS formats.
         * It also allows parsing openEHR partial date times where all date fields up to the year may be omitted.
         */
        @JvmField
        val PARTIAL_ZONE_DATE_TIME_WITH_STRICT_TIME_FORMATTER: DateTimeFormatter = DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .append(PARTIAL_DATE_FORMATTER)
            .appendOptional(DateTimeFormatterBuilder().appendLiteral("T").toFormatter())
            .append(STRICT_TIME_FORMATTER)
            .appendOptional(OFFSET_FORMATTER)
            .appendOptional(ZONE_FORMATTER)
            .toFormatter()

        /**
         * The date formatter that formats or parses a date in openEHR format without an offset.
         * This returns a formatter capable of formatting and parsing the regular (YYYYMMdd) or extended (YYYY-MM-dd) date format.
         * It also allows parsing openEHR partial dates where all fields up to the year may be omitted.
         *
         * @param compact [Boolean] true if pattern is compact
         * @param strict [Boolean] if true exact digits of time fields has to be passed
         * @return [DateTimeFormatter]
         */
        @JvmStatic
        fun partialDateFormatter(compact: Boolean = false, strict: Boolean = true): DateTimeFormatter {
            val formatterBuilder = DateTimeFormatterBuilder()
            formatterBuilder.optionalStart().appendValue(ChronoField.YEAR, 4, 4, SignStyle.EXCEEDS_PAD)
            formatterBuilder.optionalStart()
            if (!compact) {
                formatterBuilder.appendLiteral("-")
            }
            formatterBuilder.appendValue(ChronoField.MONTH_OF_YEAR, if (strict) 2 else 1, 2, SignStyle.NOT_NEGATIVE).optionalEnd()
            formatterBuilder.optionalStart()
            if (!compact) {
                formatterBuilder.appendLiteral("-")
            }

            return formatterBuilder.appendValue(ChronoField.DAY_OF_MONTH, if (strict) 2 else 1, 2, SignStyle.NOT_NEGATIVE).optionalEnd().toFormatter()
        }

        /**
         * The time formatter that formats or parses a time in openEHR format without an offset.
         * This returns a formatter capable of formatting and parsing the regular (HHmmss.S) or extended (HH:mm:ss.S) time format.
         * It also allows parsing openEHR partial times where all fields may be omitted.
         *
         * @param compact [Boolean] true if pattern is compact
         * @param strict [Boolean] if true exact digits of time fields has to be passed
         * @param forFormatting [Boolean] true if formatter will be used for formatting
         * @return [DateTimeFormatter]
         */
        @JvmStatic
        fun partialTimeFormatter(compact: Boolean = false, strict: Boolean = true, forFormatting: Boolean = true): DateTimeFormatter =
            createPartialTimeFormatter(compact, strict = strict, appendTimeLiteral = false, forFormatting)

        private fun createPartialTimeFormatter(
                compact: Boolean = false,
                strict: Boolean,
                appendTimeLiteral: Boolean = false,
                forFormatting: Boolean = true): DateTimeFormatter {
            val formatterBuilder = DateTimeFormatterBuilder()
                .optionalStart()
            if (appendTimeLiteral) {
                formatterBuilder.optionalStart().appendLiteral("T").optionalEnd()
            }
            formatterBuilder.appendValue(ChronoField.HOUR_OF_DAY, if (strict) 2 else 1, 2, SignStyle.NOT_NEGATIVE)
                .optionalStart()
            if (!compact) {
                formatterBuilder.appendLiteral(":")
            }
            formatterBuilder.appendValue(ChronoField.MINUTE_OF_HOUR, if (strict) 2 else 1, 2, SignStyle.NOT_NEGATIVE)
                .optionalStart()
            if (!compact) {
                formatterBuilder.appendLiteral(":")
            }

            formatterBuilder.appendValue(ChronoField.SECOND_OF_MINUTE, if (strict) 2 else 1, 2, SignStyle.NOT_NEGATIVE)

            if (forFormatting) {
                formatterBuilder.optionalStart().appendFraction(ChronoField.NANO_OF_SECOND, 0, 9, true).optionalEnd()
            } else {
                formatterBuilder.optionalStart().appendLiteral(".").appendFraction(ChronoField.NANO_OF_SECOND, 0, 9, false).optionalEnd()
                formatterBuilder.optionalStart().appendLiteral(",").appendFraction(ChronoField.NANO_OF_SECOND, 0, 9, false).optionalEnd()
            }

            return formatterBuilder.toFormatter().let { if (forFormatting) it.withDecimalStyle(DecimalStyle.STANDARD) else it }
        }

        /**
         * The time formatter that formats or parses a time in openEHR format with an offset.
         * This returns a formatter capable of formatting and parsing the regular (HHmmss.S, )
         * or extended (HH:mm:ss.S) time format.
         * It also allows parsing openEHR partial times where all fields may be omitted.
         *
         * @param compact [Boolean] true if pattern is compact
         * @param strict [Boolean] if true only one Zone or OffsetId parser is appended. Otherwise, all options are appended.
         * @param forFormatting [Boolean] true if formatter will be used for formatting
         * @return [DateTimeFormatter]
         */
        fun partialOffsetTimeFormatter(compact: Boolean = false, strict: Boolean = true, forFormatting: Boolean = true): DateTimeFormatter =
            DateTimeFormatterBuilder()
                .append(partialTimeFormatter(compact, strict || forFormatting, forFormatting))
                .append(offsetFormatter(compact, strict, forFormatting))
                .toFormatter()

        /**
         * The date time formatter that formats or parses a date time in openEHR format without an offset.
         * This returns a formatter capable of formatting and parsing the regular (YYYYMMdd) or extended (YYYY-MM-dd) date format
         * and regular (HHmmss.S) or extended (HH:mm:ss.S) time format.
         * It also allows parsing openEHR partial date times where all fields up to the year may be omitted.
         *
         * @param compact [Boolean] true if pattern is compact
         * @param strict [Boolean] if true exact digits of time fields has to be passed
         * @param forFormatting [Boolean] true if formatter will be used for formatting
         * @return [DateTimeFormatter]
         */
        fun partialLocalDateTimeFormatter(compact: Boolean = false, strict: Boolean, forFormatting: Boolean = true): DateTimeFormatter =
            DateTimeFormatterBuilder()
                .append(partialDateFormatter(compact, strict || forFormatting))
                .append(createPartialTimeFormatter(compact, strict || forFormatting, appendTimeLiteral = true, forFormatting))
                .toFormatter()

        /**
         * The date time formatter that formats or parses a date time in openEHR format with an offset.
         * This returns a formatter capable of formatting and parsing the regular (YYYYMMdd) or extended (YYYY-MM-dd) date format,
         * regular (HHmmss.S) or extended (HH:mm:ss.S) time format and offset in +HH, +HHmm, +HH:mm, +HHMM, +HH:MM, +HHMMss, +HH:MM:ss, +HHMMSS, +HH:MM:SS formats.
         * It also allows parsing openEHR partial date times where all fields up to the year may be omitted.
         *
         * @param compact [Boolean] true if pattern is compact
         * @param strict [Boolean] if true only one Zone or OffsetId parser is appended. Otherwise, all options are appended.
         * @param forFormatting [Boolean] true if formatter will be used for formatting
         * @return [DateTimeFormatter]
         */
        fun partialOffsetDateTimeFormatter(compact: Boolean = false, strict: Boolean = true, forFormatting: Boolean = true): DateTimeFormatter =
            DateTimeFormatterBuilder()
                .append(partialLocalDateTimeFormatter(compact, strict, forFormatting))
                .appendOptional(offsetFormatter(compact, strict, forFormatting))
                .toFormatter()

        /**
         * The date time formatter that formats or parses an offset.
         * This returns a formatter capable of formatting and parsing offset in +HH, +HHmm, +HH:mm, +HHMM, +HH:MM, +HHMMss, +HH:MM:ss, +HHMMSS, +HH:MM:SS formats.
         *
         * @param compact [Boolean] true if pattern is compact
         * @param strict [Boolean] if true only one Zone or OffsetId parser is appended. Otherwise, all options are appended.
         * @param forFormatting [Boolean] true if formatter will be used for formatting
         * @return [DateTimeFormatter]
         */
        @JvmStatic
        fun offsetFormatter(compact: Boolean = false, strict: Boolean = true, forFormatting: Boolean = true): DateTimeFormatter =
            if (compact)
                DateTimeFormatterBuilder().appendOptional(DateTimeFormatter.ofPattern("Z")).toFormatter()
            else if (forFormatting)
                if (strict)
                    DateTimeFormatterBuilder().appendZoneOrOffsetId().toFormatter()
                else
                    DateTimeFormatterBuilder().optionalStart().appendZoneOrOffsetId().optionalEnd().toFormatter()
            else
                DateTimeFormatterBuilder()
                    .parseCaseInsensitive()
                    .appendOptional(DateTimeFormatter.ofPattern("XXXXX"))
                    .appendOptional(DateTimeFormatter.ofPattern("XXXX"))
                    .appendOptional(DateTimeFormatter.ofPattern("XXX"))
                    .appendOptional(DateTimeFormatter.ofPattern("XX"))
                    .appendOptional(DateTimeFormatter.ofPattern("X"))
                    .toFormatter()
    }
}
