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

package care.better.platform.time.format

import care.better.platform.time.OpenEhrDateTimeFormatterContext
import care.better.platform.time.temporal.OpenEhrField
import care.better.platform.time.temporal.OpenEhrFieldState
import java.time.DateTimeException
import java.time.chrono.IsoChronology
import java.time.format.*
import java.time.temporal.ChronoField
import java.util.*
import java.util.regex.Pattern

/**
 * @author Matic Ribic
 */
class OpenEhrDateTimeFormatterBuilder {
    private var formatterBuilder = DateTimeFormatterBuilder()
    private var timeFormatterBuilder: DateTimeFormatterBuilder? = null
    private var resolverStyle = ResolverStyle.SMART
    private var compact = false
    private var pattern: String? = null
    private var patternFieldStates = mapOf<OpenEhrField, OpenEhrFieldState>()

    companion object {
        private val DATE_TIME_REGEX_PATTERN: Pattern =
            Pattern.compile(
                    "([Y]{4})?(-)?([MX?]{2})?(-)?([DX?]{2})?(T)?([HX?]{2})?(:)?([MX?]{2})?(:)?([SX?]{2})?(?:.([SX?]{3}))?(Z)?",
                    Pattern.CASE_INSENSITIVE
            )
        private val COMPACT_PARTIAL_DATE_TIME_REGEX_PATTERN: Pattern =
            Pattern.compile("([Y]{4})?([MX?]{2})?([DX?]{2})?(T)?([HX?]{2})?([MX?]{2})?([SX?]{2})?(.[SX?]{3})?(Z)?", Pattern.CASE_INSENSITIVE)

        private val APPEND_YEAR: (DateTimeFormatterBuilder) -> DateTimeFormatterBuilder =
            { it.appendValue(ChronoField.YEAR, 4, 4, SignStyle.EXCEEDS_PAD) }
        private val APPEND_MONTH: (DateTimeFormatterBuilder) -> DateTimeFormatterBuilder =
            { it.appendValue(ChronoField.MONTH_OF_YEAR, 2, 2, SignStyle.NOT_NEGATIVE) }
        private val APPEND_DAY: (DateTimeFormatterBuilder) -> DateTimeFormatterBuilder =
            { it.appendValue(ChronoField.DAY_OF_MONTH, 2, 2, SignStyle.NOT_NEGATIVE) }
        private val APPEND_HOUR: (DateTimeFormatterBuilder) -> DateTimeFormatterBuilder =
            { it.appendValue(ChronoField.HOUR_OF_DAY, 2, 2, SignStyle.NOT_NEGATIVE) }
        private val APPEND_MINUTE: (DateTimeFormatterBuilder) -> DateTimeFormatterBuilder =
            { it.appendValue(ChronoField.MINUTE_OF_HOUR, 2, 2, SignStyle.NOT_NEGATIVE) }
        private val APPEND_SECOND: (DateTimeFormatterBuilder) -> DateTimeFormatterBuilder =
            { it.appendValue(ChronoField.SECOND_OF_MINUTE, 2, 2, SignStyle.NOT_NEGATIVE) }
        private val APPEND_NANO: (DateTimeFormatterBuilder) -> DateTimeFormatterBuilder =
            { it.appendFraction(ChronoField.NANO_OF_SECOND, 3, 6, true) }

        private fun appendPattern(
                formatterBuilder: DateTimeFormatterBuilder,
                pattern: String,
                strict: Boolean,
                fieldStates: Map<OpenEhrField, OpenEhrFieldState>
        ): DateTimeFormatterBuilder {

            if (fieldStates[OpenEhrField.MINUTES].let { it == null || it == OpenEhrFieldState.FORBIDDEN }
                    && fieldStates[OpenEhrField.OFFSET_SECONDS] != OpenEhrFieldState.FORBIDDEN) {
                throw DateTimeException("Invalid pattern $pattern")
            }

            val compact = isCompactPattern(pattern)
            formatterBuilder.appendField(APPEND_YEAR, fieldStates[OpenEhrField.YEARS], compact)
            formatterBuilder.appendField(APPEND_MONTH, fieldStates[OpenEhrField.MONTHS], compact, "-")
            formatterBuilder.appendField(APPEND_DAY, fieldStates[OpenEhrField.DAYS], compact, "-")

            if (fieldStates[OpenEhrField.DAYS] != null)
                formatterBuilder.appendField(APPEND_HOUR, fieldStates[OpenEhrField.HOURS], compact, "T", true)
            else
                formatterBuilder.appendField(APPEND_HOUR, fieldStates[OpenEhrField.HOURS], compact)

            formatterBuilder.appendField(APPEND_MINUTE, fieldStates[OpenEhrField.MINUTES], compact, ":")
            formatterBuilder.appendField(APPEND_SECOND, fieldStates[OpenEhrField.SECONDS], compact, ":")
            formatterBuilder.appendField(APPEND_NANO, fieldStates[OpenEhrField.NANOS], compact)

            // time zone offset
            val timeZoneAppender: (DateTimeFormatterBuilder) -> DateTimeFormatterBuilder =
                {
                    if (compact)
                        it.optionalStart().appendOffset("+HHMM", "Z").optionalEnd()
                    else
                        it.optionalStart().appendZoneOrOffsetId().optionalEnd()
                }

            val offsetState = fieldStates[OpenEhrField.OFFSET_SECONDS]
            if (strict && offsetState == OpenEhrFieldState.MANDATORY) {
                timeZoneAppender.invoke(formatterBuilder)
            } else if (offsetState == OpenEhrFieldState.MANDATORY || offsetState == OpenEhrFieldState.OPTIONAL) {
                formatterBuilder.optionalStart()
                timeZoneAppender.invoke(formatterBuilder)
                formatterBuilder.optionalEnd()
            }

            return formatterBuilder
        }

        private fun DateTimeFormatterBuilder.appendField(
                appender: (DateTimeFormatterBuilder) -> DateTimeFormatterBuilder,
                fieldState: OpenEhrFieldState?,
                compact: Boolean,
                separator: String? = null,
                forceSeparator: Boolean = false
        ): DateTimeFormatterBuilder =
            if (fieldState == OpenEhrFieldState.MANDATORY) {
                if (!compact || forceSeparator) separator?.let { this.appendLiteral(it) }
                appender.invoke(this)
            } else if (fieldState == OpenEhrFieldState.OPTIONAL || fieldState == OpenEhrFieldState.UNDEFINED) {
                this.optionalStart()
                if (!compact || forceSeparator) separator?.let { this.appendLiteral(it) }
                appender.invoke(this)
                this.optionalEnd()
            } else {
                this
            }

        fun getFieldStates(pattern: String): Map<OpenEhrField, OpenEhrFieldState> {
            val matcher = DATE_TIME_REGEX_PATTERN.matcher(pattern.uppercase())
            if (!matcher.matches()) {
                throw DateTimeException("Invalid pattern $pattern")
            }

            val year = matcher.group(1)
            val month = matcher.group(3)
            val day = matcher.group(5)
            val hour = matcher.group(7)
            val minute = matcher.group(9)
            val second = matcher.group(11)
            val nanosecond = matcher.group(12)
            val timeZone = matcher.group(13)

            if ((minute == null || minute == "XX") && timeZone != null) {
                throw DateTimeException("Invalid pattern $pattern")
            }

            val fieldStates = mutableMapOf<OpenEhrField, OpenEhrFieldState>()
            putFieldState(fieldStates, OpenEhrField.YEARS, year, "YYYY")
            putFieldState(fieldStates, OpenEhrField.MONTHS, month, "MM")
            putFieldState(fieldStates, OpenEhrField.DAYS, day, "DD")
            putFieldState(fieldStates, OpenEhrField.HOURS, hour, "HH")
            putFieldState(fieldStates, OpenEhrField.MINUTES, minute, "MM")
            putFieldState(fieldStates, OpenEhrField.SECONDS, second, "SS")
            putFieldState(fieldStates, OpenEhrField.NANOS, nanosecond, "SSS")

            when {
                timeZone != null -> OpenEhrFieldState.MANDATORY
                !existDateFields(fieldStates) ->
                    when (fieldStates[OpenEhrField.SECONDS]) {
                        OpenEhrFieldState.MANDATORY, OpenEhrFieldState.OPTIONAL, OpenEhrFieldState.UNDEFINED -> OpenEhrFieldState.OPTIONAL
                        null, OpenEhrFieldState.FORBIDDEN -> when (fieldStates[OpenEhrField.MINUTES]) {
                            OpenEhrFieldState.MANDATORY, OpenEhrFieldState.OPTIONAL, OpenEhrFieldState.UNDEFINED -> OpenEhrFieldState.OPTIONAL
                            else -> OpenEhrFieldState.FORBIDDEN
                        }
                    }
                fieldStates[OpenEhrField.MINUTES]?.possible == true -> OpenEhrFieldState.OPTIONAL
                fieldStates[OpenEhrField.SECONDS] == null && fieldStates[OpenEhrField.MINUTES]?.possible == true -> OpenEhrFieldState.OPTIONAL
                fieldStates[OpenEhrField.SECONDS]?.possible != true && fieldStates[OpenEhrField.MINUTES]?.possible != true -> OpenEhrFieldState.FORBIDDEN
                else -> null
            }?.let {
                fieldStates[OpenEhrField.OFFSET_SECONDS] = it
            }

            return fieldStates
        }

        private fun existDateFields(fieldStates: Map<OpenEhrField, OpenEhrFieldState>) =
            arrayOf(OpenEhrField.YEARS, OpenEhrField.MONTHS, OpenEhrField.DAYS).any { fieldStates[it] != null }

        fun isCompactPattern(pattern: String?) =
            pattern?.isNotBlank() == true && pattern.uppercase().let { it != "YYYY" && it != "HH" }
                    && COMPACT_PARTIAL_DATE_TIME_REGEX_PATTERN.matcher(pattern).matches()

        private fun putFieldState(
                fieldStates: MutableMap<OpenEhrField, OpenEhrFieldState>,
                field: OpenEhrField,
                actualValue: String?,
                expectedPatternValue: String
        ) {
            when (actualValue) {
                expectedPatternValue -> fieldStates[field] = OpenEhrFieldState.MANDATORY
                "?".repeat(expectedPatternValue.length) -> fieldStates[field] = OpenEhrFieldState.OPTIONAL
                "X".repeat(expectedPatternValue.length) -> fieldStates[field] = OpenEhrFieldState.FORBIDDEN
            }
        }
    }

    fun appendPattern(pattern: String?, strict: Boolean): OpenEhrDateTimeFormatterBuilder {
        val matcher = DATE_TIME_REGEX_PATTERN.matcher(pattern?.uppercase() ?: "")
        if (pattern?.isNotBlank() == true && !matcher.matches()) {
            throw DateTimeException("Invalid pattern $pattern")
        }

        this.pattern = pattern
        resolverStyle = if (strict) ResolverStyle.STRICT else ResolverStyle.SMART

        if (pattern.isNullOrBlank()) {
            this.pattern = pattern
            formatterBuilder.append(DateTimeFormatters.partialOffsetDateTimeFormatter(compact, false, true))
            timeFormatterBuilder =
                (timeFormatterBuilder ?: DateTimeFormatterBuilder()).append(DateTimeFormatters.partialOffsetTimeFormatter(compact, false, true))
            patternFieldStates = OpenEhrField.values().map { it to OpenEhrFieldState.UNDEFINED }.toMap()
            return this
        }

        compact = isCompactPattern(pattern)
        patternFieldStates = getFieldStates(pattern)

        appendPattern(formatterBuilder, pattern, strict, patternFieldStates)

        return this
    }

    fun toOpenEhrFormatter(locale: Locale? = null): OpenEhrDateTimeFormatter {
        val validLocale = locale ?: Locale.getDefault(Locale.Category.FORMAT)
        val defaultFormatter = toFormatter(formatterBuilder, validLocale)
        return OpenEhrDateTimeFormatter(
                defaultFormatter,
                if (timeFormatterBuilder != null) toFormatter(timeFormatterBuilder!!, validLocale) else defaultFormatter,
                OpenEhrDateTimeFormatterContext(
                        patternFieldStates.toMap(),
                        pattern,
                        resolverStyle,
                        compact,
                        validLocale)
        )
    }

    private fun toFormatter(formatterBuilder: DateTimeFormatterBuilder, locale: Locale): DateTimeFormatter = formatterBuilder
        .toFormatter(locale)
        .withChronology(IsoChronology.INSTANCE)
        .withDecimalStyle(DecimalStyle.STANDARD)
        .withResolverStyle(resolverStyle)
}
