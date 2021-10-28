package care.better.platform.time.format

import care.better.platform.time.OpenEhrDateTimeFormatterContext
import care.better.platform.time.temporal.*
import java.text.ParsePosition
import java.time.*
import java.time.format.DateTimeFormatter
import java.time.format.ResolverStyle
import java.time.temporal.ChronoField
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalAccessor
import java.time.temporal.TemporalQueries
import java.util.*

/**
 * @author Matic Ribic
 */
class OpenEhrDateTimeFormatter(
        private val defaultFormatter: DateTimeFormatter,
        private val timeFormatter: DateTimeFormatter,
        val context: OpenEhrDateTimeFormatterContext) {

    companion object {
        private val MIN_OPEN_EHR_FIELD_FOR_OFFSET = OpenEhrField.SECONDS
        private val MIN_ISO_OPEN_EHR_FIELD_FOR_OFFSET = OpenEhrField.MINUTES
        private val MIN_CHRONO_FIELD_FOR_OFFSET = MIN_OPEN_EHR_FIELD_FOR_OFFSET.field
        private val MIN_ISO_CHRONO_FIELD_FOR_OFFSET = MIN_ISO_OPEN_EHR_FIELD_FOR_OFFSET.field

        @JvmStatic
        @JvmOverloads
        fun ofPattern(pattern: String?, strict: Boolean = true, locale: Locale? = null): OpenEhrDateTimeFormatter =
            OpenEhrDateTimeFormatterBuilder().appendPattern(pattern, strict).toOpenEhrFormatter(locale)

        @JvmStatic
        @JvmOverloads
        fun ofLocalDate(strict: Boolean = true, locale: Locale? = null): OpenEhrDateTimeFormatter =
            OpenEhrDateTimeFormatterBuilder().appendPattern("yyyy-MM-dd", strict).toOpenEhrFormatter(locale)

        @JvmStatic
        @JvmOverloads
        fun ofOptionalLocalDate(strict: Boolean = true, locale: Locale? = null): OpenEhrDateTimeFormatter =
            OpenEhrDateTimeFormatterBuilder().appendPattern("yyyy-??-??", strict).toOpenEhrFormatter(locale)

        @JvmStatic
        @JvmOverloads
        fun ofLocalDateTime(strict: Boolean = true, locale: Locale? = null): OpenEhrDateTimeFormatter =
            OpenEhrDateTimeFormatterBuilder().appendPattern("yyyy-MM-ddTHH:mm:SS", strict).toOpenEhrFormatter(locale)

        @JvmStatic
        @JvmOverloads
        fun ofOffsetDateTime(strict: Boolean = true, locale: Locale? = null): OpenEhrDateTimeFormatter =
            OpenEhrDateTimeFormatterBuilder().appendPattern("yyyy-MM-ddTHH:mm:SSZ", strict).toOpenEhrFormatter(locale)

        @JvmStatic
        @JvmOverloads
        fun ofOptionalOffsetDateTime(strict: Boolean = true, locale: Locale? = null): OpenEhrDateTimeFormatter =
            OpenEhrDateTimeFormatterBuilder().appendPattern("yyyy-MM-ddTHH:mm:??.???Z", strict).toOpenEhrFormatter(locale)

        @JvmStatic
        @JvmOverloads
        fun ofOptionalLocalDateTime(strict: Boolean = true, locale: Locale? = null): OpenEhrDateTimeFormatter =
            OpenEhrDateTimeFormatterBuilder().appendPattern("yyyy-??-??T??:??:??", strict).toOpenEhrFormatter(locale)

        @JvmStatic
        @JvmOverloads
        fun ofLocalTime(strict: Boolean = true, locale: Locale? = null): OpenEhrDateTimeFormatter =
            OpenEhrDateTimeFormatterBuilder().appendPattern("HH:mm:SS", strict).toOpenEhrFormatter(locale)

        @JvmStatic
        @JvmOverloads
        fun ofOptionalLocalTime(strict: Boolean = true, locale: Locale? = null): OpenEhrDateTimeFormatter =
            OpenEhrDateTimeFormatterBuilder().appendPattern("HH:??:??", strict).toOpenEhrFormatter(locale)

        @JvmStatic
        @JvmOverloads
        fun ofOffsetTime(strict: Boolean = true, locale: Locale? = null): OpenEhrDateTimeFormatter =
            OpenEhrDateTimeFormatterBuilder().appendPattern("HH:mm:SSZ", strict).toOpenEhrFormatter(locale)

        @JvmStatic
        @JvmOverloads
        fun ofOptionalOffsetTime(strict: Boolean = true, locale: Locale? = null): OpenEhrDateTimeFormatter =
            OpenEhrDateTimeFormatterBuilder().appendPattern("HH:mm:??.???Z", strict).toOpenEhrFormatter(locale)
    }

    fun parseDate(text: String): TemporalAccessor = parseDate(text, { it, _, _ -> it })

    fun <T : TemporalAccessor> parseDate(text: String, query: OpenEhrTemporalQuery<T>): T =
        parseBestDate(text, *(arrayOf(query))) as T

    fun parseBestDate(text: String, vararg queries: OpenEhrTemporalQuery<out TemporalAccessor>): TemporalAccessor =
        OpenEhrDateTimeParser(context.filterFields(includeDate = true, includeTime = false)).parseBestDate(text, *queries)

    fun parseTime(text: String): TemporalAccessor = parseTime(text, { it, _, _ -> it })

    fun <T : TemporalAccessor> parseTime(text: String, query: OpenEhrTemporalQuery<T>): T =
        parseBestTime(text, *(arrayOf(query))) as T

    fun parseBestTime(text: String, vararg queries: OpenEhrTemporalQuery<out TemporalAccessor>): TemporalAccessor =
        OpenEhrDateTimeParser(context.filterFields(includeDate = false, includeTime = true)).parseBestTime(text, *queries)

    fun parseDateTime(text: String): TemporalAccessor = parseDateTime(text, { it, _, _ -> it })

    fun <T : TemporalAccessor> parseDateTime(text: String, query: OpenEhrTemporalQuery<T>): T =
        parseBestDateTime(text, *(arrayOf(query))) as T

    fun parseBestDateTime(text: String, vararg queries: OpenEhrTemporalQuery<out TemporalAccessor>): TemporalAccessor =
        OpenEhrDateTimeParser(context.filterFields(includeDate = true, includeTime = true)).parseBestDateTime(text, *queries)

    fun format(temporal: TemporalAccessor): String {
        validateBeforeFormatting(temporal)
        var validatedTemporal = temporal
        val hasZone = when (temporal) {
            is OffsetDateTime, is OpenEhrOffsetDateTime, is ZonedDateTime, is OffsetTime, is OpenEhrOffsetTime ->
                true
            else -> false
        }

        val addOffset = !hasZone && if (context.resolverStyle != ResolverStyle.STRICT) {
            when (context.patternFieldStates[OpenEhrField.OFFSET_SECONDS]) {
                null, OpenEhrFieldState.MANDATORY -> temporal.isSupported(MIN_CHRONO_FIELD_FOR_OFFSET)
                OpenEhrFieldState.UNDEFINED, OpenEhrFieldState.OPTIONAL -> temporal.isSupported(MIN_CHRONO_FIELD_FOR_OFFSET)
                OpenEhrFieldState.FORBIDDEN -> false
            }
        } else {
            temporal.isSupported(MIN_CHRONO_FIELD_FOR_OFFSET) &&
                    (context.patternFieldStates[OpenEhrField.OFFSET_SECONDS]?.let { it == OpenEhrFieldState.OPTIONAL || it == OpenEhrFieldState.UNDEFINED }
                        ?: true)
        }

        if (addOffset) {
            validatedTemporal = when (temporal) {
                is LocalDateTime -> temporal.atOffset(ZoneId.systemDefault().rules.getOffset(temporal))
                is OpenEhrLocalDateTime -> temporal.atOffset(ZoneId.systemDefault().rules.getOffset(temporal.dateTime))
                else -> temporal
            }
        }

        val removeOffset = context.resolverStyle != ResolverStyle.STRICT && hasZone &&
                when (context.patternFieldStates[MIN_OPEN_EHR_FIELD_FOR_OFFSET]) {
                    null -> OpenEhrField.DATE_TIME_FIELDS.asSequence().filter { it < MIN_OPEN_EHR_FIELD_FOR_OFFSET }
                        .none { context.isFieldPossible(it) } && !temporal.isSupported(MIN_ISO_CHRONO_FIELD_FOR_OFFSET)
                    OpenEhrFieldState.MANDATORY -> !temporal.isSupported(MIN_CHRONO_FIELD_FOR_OFFSET)
                    OpenEhrFieldState.UNDEFINED, OpenEhrFieldState.OPTIONAL -> !temporal.isSupported(MIN_CHRONO_FIELD_FOR_OFFSET)
                            && !(context.undefinedPattern && temporal.isSupported(MIN_ISO_CHRONO_FIELD_FOR_OFFSET))
                    OpenEhrFieldState.FORBIDDEN -> true
                }

        if (removeOffset) {
            validatedTemporal = when (temporal) {
                is OffsetDateTime -> temporal.withOffsetSameInstant(ZoneId.systemDefault().rules.getOffset(temporal.toInstant())).toLocalDateTime()
                is OpenEhrOffsetDateTime -> temporal.withOffsetSameInstant(ZoneId.systemDefault().rules.getOffset(temporal.dateTime.toInstant()))
                    .toLocalDateTime()
                is ZonedDateTime -> temporal.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime()
                else -> temporal
            }
        }

        return (defaultFormatter.takeUnless { isTime(validatedTemporal) } ?: timeFormatter).format(validatedTemporal)
    }

    private fun validateBeforeFormatting(temporal: TemporalAccessor) {
        if (context.resolverStyle == ResolverStyle.STRICT) {
            require((OpenEhrField.DATE_TIME_FIELDS.takeUnless { isTime(temporal) } ?: OpenEhrField.TIME_FIELDS).none {
                when (context.patternDateTimeFieldStates[it]) {
                    OpenEhrFieldState.MANDATORY -> !temporal.isSupported(it.field)
                    OpenEhrFieldState.FORBIDDEN -> temporal.isSupported(it.field)
                    OpenEhrFieldState.UNDEFINED, OpenEhrFieldState.OPTIONAL -> false
                    // nano seconds are special case, because of primitive type in LocalDateTime and LocalTime
                    null -> it.field != ChronoField.NANO_OF_SECOND &&
                            (temporal.isSupported(it.field) || temporal is OpenEhrTemporal<*> && temporal.isStrictlySupported(it))
                }
            }) { "Invalid value \"$temporal\" for pattern \"${context.pattern}\"" }
        }
    }

    private fun isTime(temporal: TemporalAccessor) = OpenEhrField.DATE_FIELDS.all { !temporal.isSupported(it.field) }

    private class OpenEhrDateTimeParser(val context: OpenEhrDateTimeFormatterContext) {
        private val dateTimeFormatter = DateTimeFormatters.partialOffsetDateTimeFormatter(context.compact, context.strict, false)
        private val dateFormatter = DateTimeFormatters.partialDateFormatter(context.compact, context.strict)
        private val timeFormatter = DateTimeFormatters.partialOffsetTimeFormatter(context.compact, context.strict, false)

        fun <T : TemporalAccessor> parseBestDate(text: String, vararg queries: OpenEhrTemporalQuery<out T>): T =
            try {
                getParsedUnresolved(text, dateTimeFormatter).let { parsedAndField ->
                    validateBeforeParsing(text, dateFormatter, null)
                    val precisionField = parsedAndField.second

                    val parsed = dateTimeFormatter.parseBest(
                            text,
                            { OpenEhrOffsetDateTime.from(it, precisionField, context).toLocalDateTime().toLocalDate() },
                            { OpenEhrLocalDateTime.from(it, precisionField, context).toLocalDate() })

                    val modifiedParsed = if (parsed is OpenEhrTemporal<*>) parsed.toExactTemporal() else parsed
                    resolveTemporal(modifiedParsed, precisionField, *queries)
                }
            } catch (e: IllegalArgumentException) {
                throw DateTimeException("Invalid date \"$text\" for pattern \"${context.pattern}\"")
            } catch (e: IllegalArgumentException) {
                throw DateTimeException("Invalid date \"$text\" for pattern \"${context.pattern}\"")
            }

        fun <T : TemporalAccessor> parseBestTime(text: String, vararg queries: OpenEhrTemporalQuery<out T>): T {
            try {
                val validatedText = if (context.resolverStyle != ResolverStyle.STRICT && text.contains("T")) text.substring(text.indexOf("T") + 1) else text

                return getParsedUnresolved(validatedText, timeFormatter).let { parsedAndField ->

                    validateBeforeParsing(
                            text,
                            timeFormatter.takeUnless { context.resolverStyle != ResolverStyle.STRICT && text.contains("T") } ?: dateTimeFormatter,
                            ResolverStyle.STRICT)

                    val precisionField = parsedAndField.second

                    with(timeFormatter.takeUnless { text.contains("T") } ?: dateTimeFormatter) {
                        val parsed = when {
                            !context.isPartialPattern() &&
                                    arrayOf(OpenEhrField.MINUTES, OpenEhrField.SECONDS).all {
                                        isExactlyPrecisionField(it, precisionField)
                                    } -> this.parseBest(
                                    text,
                                    { OffsetTime.from(it) },
                                    { LocalTime.from(it) })
                            else -> this.parseBest(
                                    text,
                                    { OpenEhrOffsetTime.from(it, precisionField, context) },
                                    { OpenEhrLocalTime.from(it, precisionField, context) })
                        }

                        val modifiedParsed = if (parsed is OpenEhrTemporal<*>) parsed.toExactTemporal() else parsed
                        resolveTemporal(modifiedParsed, precisionField, *queries)
                    }
                }
            } catch (e: IllegalArgumentException) {
                throw DateTimeException("Invalid time \"$text\" for pattern \"${context.pattern}\"")
            } catch (e: DateTimeException) {
                throw DateTimeException("Invalid time \"$text\" for pattern \"${context.pattern}\"")
            }
        }

        fun <T : TemporalAccessor> parseBestDateTime(text: String, vararg queries: OpenEhrTemporalQuery<out T>): T =
            try {
                getParsedUnresolved(text, dateTimeFormatter).let { parsedAndField ->
                    validateBeforeParsing(
                            text,
                            dateTimeFormatter,
                            ResolverStyle.STRICT.takeIf { context.resolverStyle == ResolverStyle.STRICT } ?: ResolverStyle.SMART)

                    val precisionField = parsedAndField.second

                    with(dateTimeFormatter) {
                        val containsAllRequiredFields = OpenEhrField.DATE_TIME_FIELDS.asSequence()
                            .filter { it <= MIN_OPEN_EHR_FIELD_FOR_OFFSET }
                            .any { isExactlyPrecisionField(it, precisionField) }
                        val parsed = when {
                            !context.isPartialPattern() && containsAllRequiredFields -> this.parseBest(
                                    text,
                                    { OffsetDateTime.from(it) },
                                    { LocalDateTime.from(it) })
                            else -> this.parseBest(
                                    text,
                                    { OpenEhrOffsetDateTime.from(it, precisionField, context) },
                                    { OpenEhrLocalDateTime.from(it, precisionField, context) })
                        }

                        val modifiedParsed = if (parsed is OpenEhrTemporal<*>) parsed.toExactTemporal() else parsed
                        resolveTemporal(modifiedParsed, precisionField, *queries)
                    }
                }
            } catch (e: IllegalArgumentException) {
                throw DateTimeException("Invalid datetime \"$text\" for pattern \"${context.pattern}\"")
            } catch (e: DateTimeException) {
                throw DateTimeException("Invalid datetime \"$text\" for pattern \"${context.pattern}\"")
            }

        private fun <T : TemporalAccessor> resolveTemporal(
                temporal: TemporalAccessor,
                precisionField: OpenEhrField,
                vararg queries: OpenEhrTemporalQuery<out T>): T {
            if (queries.isEmpty()) {
                return temporal as T
            }

            for (query in queries) {
                try {
                    return query.queryFrom(temporal, precisionField, context)
                } catch (ex: RuntimeException) {
                    // continue
                }
            }

            throw DateTimeException("Invalid value \"$temporal\" for pattern \"${context.pattern}\"")
        }

        private fun isExactlyPrecisionField(field: OpenEhrField, precisionField: OpenEhrField): Boolean =
            field == precisionField && context.patternFieldStates[precisionField] == OpenEhrFieldState.MANDATORY
                    && (precisionField.ordinal - 1 == 0 || !context.isFieldPossible(OpenEhrField.values()[precisionField.ordinal - 1]))

        private fun getParsedUnresolved(text: String, formatter: DateTimeFormatter): Pair<TemporalAccessor, OpenEhrField> {
            val parseUnresolved = formatter.parseUnresolved(text, ParsePosition(0))
            val field = getPrecisionField(parseUnresolved)
                .let { precisionField ->
                    context.patternFieldStates.entries.asSequence().filter { it.value.possible && it.key >= precisionField }
                        .sortedBy { it.key }.firstOrNull()?.key ?: throw DateTimeException("Invalid value \"$text\" for pattern \"${context.pattern}\"")
                }
            return Pair(parseUnresolved, field)
        }

        private fun getPrecisionField(temporal: TemporalAccessor): OpenEhrField =
            ChronoField.values().firstOrNull { temporal.isSupported(it) }?.let { OpenEhrField.valueOf(it.baseUnit as ChronoUnit) }
                ?: throw DateTimeException("Invalid value $temporal")

        private fun validateBeforeParsing(text: String, formatter: DateTimeFormatter, offsetValidationStyle: ResolverStyle?) {
            val parseUnresolved = (formatter.takeUnless { text.contains("T") } ?: dateTimeFormatter).parseUnresolved(text, ParsePosition(0))
            val strict = context.resolverStyle == ResolverStyle.STRICT
            if (strict) {
                require(OpenEhrField.DATE_TIME_FIELDS.all {
                    when (context.patternDateTimeFieldStates[it]) {
                        OpenEhrFieldState.MANDATORY -> parseUnresolved.isSupported(it.field)
                        OpenEhrFieldState.FORBIDDEN -> !parseUnresolved.isSupported(it.field)
                        OpenEhrFieldState.UNDEFINED, OpenEhrFieldState.OPTIONAL -> true
                        null -> !parseUnresolved.isSupported(it.field) || couldValueBeIgnored(it, parseUnresolved)
                    }
                }) { "Invalid value \"$text\" for pattern \"${context.pattern}\"" }
            }

            if (offsetValidationStyle != null) {
                validateOffset(text, parseUnresolved, strict, offsetValidationStyle)
            }
        }

        private fun couldValueBeIgnored(chronoField: ChronoField, temporal: TemporalAccessor) = try {
            couldValueBeIgnored(OpenEhrField.valueOf(chronoField), temporal)
        } catch (_: java.lang.IllegalArgumentException) {
            true
        }

        private fun couldValueBeIgnored(field: OpenEhrField, temporal: TemporalAccessor) = field == OpenEhrField.NANOS
                && context.patternFieldStates[field]?.let { it == OpenEhrFieldState.OPTIONAL } ?: true && temporal.getLong(field.field) == 0L

        private fun validateOffset(text: String, parseUnresolved: TemporalAccessor, strict: Boolean, offsetValidationStyle: ResolverStyle?) {
            val hasTimeZone = parseUnresolved.query(TemporalQueries.zone()) != null

            val validIsoDate = !hasTimeZone || parseUnresolved.isSupported(ChronoField.MINUTE_OF_HOUR)
            require(validIsoDate) { "Invalid value \"$text\" for pattern \"${context.pattern}\"" }

            if (strict) {
                require(when (context.patternFieldStates[OpenEhrField.OFFSET_SECONDS]) {
                            OpenEhrFieldState.MANDATORY -> hasTimeZone
                            null, OpenEhrFieldState.OPTIONAL, OpenEhrFieldState.UNDEFINED -> !hasTimeZone || (OpenEhrField.DATE_TIME_FIELDS.asSequence()
                                .filter { it < MIN_OPEN_EHR_FIELD_FOR_OFFSET }
                                .all {
                                    when (context.patternFieldStates[it]) {
                                        OpenEhrFieldState.MANDATORY -> parseUnresolved.isSupported(it.field)
                                        OpenEhrFieldState.UNDEFINED, OpenEhrFieldState.OPTIONAL -> true
                                        OpenEhrFieldState.FORBIDDEN -> !parseUnresolved.isSupported(it.field)
                                        null -> !parseUnresolved.isSupported(it.field) || couldValueBeIgnored(it.field, parseUnresolved)
                                    }
                                }
                                    && when (context.patternFieldStates[MIN_OPEN_EHR_FIELD_FOR_OFFSET]) {
                                OpenEhrFieldState.MANDATORY -> parseUnresolved.isSupported(MIN_OPEN_EHR_FIELD_FOR_OFFSET.field)
                                OpenEhrFieldState.UNDEFINED, OpenEhrFieldState.OPTIONAL -> parseUnresolved.isSupported(MIN_OPEN_EHR_FIELD_FOR_OFFSET.field)
                                        || (context.undefinedPattern && parseUnresolved.isSupported(MIN_ISO_CHRONO_FIELD_FOR_OFFSET))
                                null -> !parseUnresolved.isSupported(MIN_OPEN_EHR_FIELD_FOR_OFFSET.field)
                                OpenEhrFieldState.FORBIDDEN -> false
                            }
                                    )
                            OpenEhrFieldState.FORBIDDEN -> !hasTimeZone
                        }) { "Invalid value \"$text\" for pattern \"${context.pattern}\"" }
            } else if (offsetValidationStyle != null) {
                require(
                        when (context.patternFieldStates[OpenEhrField.OFFSET_SECONDS]) {
                            OpenEhrFieldState.MANDATORY -> offsetValidationStyle == ResolverStyle.SMART || hasTimeZone
                            null, OpenEhrFieldState.UNDEFINED, OpenEhrFieldState.OPTIONAL -> true
                            OpenEhrFieldState.FORBIDDEN -> offsetValidationStyle == ResolverStyle.SMART || !hasTimeZone
                        }) { "Invalid value \"$text\" for pattern \"${context.pattern}\"" }
            }
        }
    }
}