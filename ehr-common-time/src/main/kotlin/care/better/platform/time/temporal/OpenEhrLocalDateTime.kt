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

package care.better.platform.time.temporal

import care.better.platform.time.OpenEhrDateTimeFormatterContext
import java.io.Serializable
import java.time.*
import java.time.format.ResolverStyle
import java.time.temporal.*

/**
 * @author Matic Ribic
 */
class OpenEhrLocalDateTime(
        dateTime: LocalDateTime,
        precisionField: OpenEhrField,
        fieldStates: Map<OpenEhrField, OpenEhrFieldState>,
        resolverStyle: ResolverStyle) : OpenEhrTemporal<LocalDateTime>(dateTime, precisionField, fieldStates, resolverStyle), Comparable<OpenEhrLocalDateTime>,
    Serializable {

    companion object {

        @JvmStatic
        fun now(): OpenEhrLocalDateTime = now(Clock.systemDefaultZone())

        @JvmStatic
        fun now(clock: Clock): OpenEhrLocalDateTime =
            OpenEhrLocalDateTime(LocalDateTime.now(clock), OpenEhrField.MINIMUM, getDefaultFieldStates(OpenEhrField.DATE_TIME_FIELDS), ResolverStyle.SMART)

        @JvmStatic
        @JvmOverloads
        fun of(
                year: Int,
                month: Int? = null,
                dayOfMonth: Int? = null,
                hour: Int? = null,
                minute: Int? = null,
                second: Int? = null,
                nano: Int? = null): OpenEhrLocalDateTime =
            of(year, month, dayOfMonth, hour, minute, second, nano, getDefaultFieldStates(OpenEhrField.DATE_TIME_FIELDS))

        @JvmStatic
        fun of(
                year: Int,
                month: Int? = null,
                dayOfMonth: Int? = null,
                hour: Int? = null,
                minute: Int? = null,
                second: Int? = null,
                nano: Int? = null,
                fieldStates: Map<OpenEhrField, OpenEhrFieldState>,
                resolverStyle: ResolverStyle = ResolverStyle.SMART): OpenEhrLocalDateTime =
            OpenEhrLocalDateTime(
                    LocalDateTime.of(year, month ?: 1, dayOfMonth ?: 1, hour ?: 0, minute ?: 0, second ?: 0, nano ?: 0),
                    (OpenEhrLocalTime.getPrecision(hour, minute, second, nano) ?: OpenEhrLocalDate.getPrecision(year, month, dayOfMonth)),
                    fieldStates,
                    resolverStyle)

        @JvmStatic
        fun from(temporal: TemporalAccessor, precisionField: OpenEhrField, context: OpenEhrDateTimeFormatterContext): OpenEhrLocalDateTime {
            if (temporal is OpenEhrLocalDateTime) {
                return temporal
            }
            require(temporal.query(TemporalQueries.zone()) == null, { "temporal" })

            return when (precisionField) {
                OpenEhrField.YEARS -> Year.from(temporal).atDay(1).atStartOfDay()
                OpenEhrField.MONTHS -> YearMonth.from(temporal).atDay(1).atStartOfDay()
                OpenEhrField.DAYS -> LocalDate.from(temporal).atStartOfDay()
                else -> LocalDateTime.from(temporal)
            }.let { OpenEhrLocalDateTime(it, precisionField, context.patternFieldStates, context.resolverStyle) }
        }
    }

    val dateTime: LocalDateTime = temporal

    override fun with(field: TemporalField, newValue: Long): Temporal =
        OpenEhrLocalDateTime(dateTime.with(field, newValue), withTemporalField(field), fieldStates, resolverStyle)

    override fun plus(amountToAdd: Long, unit: TemporalUnit): Temporal =
        OpenEhrLocalDateTime(dateTime.plus(amountToAdd, unit), withTemporalUnit(unit), fieldStates, resolverStyle)

    fun toLocalDate(): OpenEhrLocalDate =
        OpenEhrLocalDate(dateTime.toLocalDate(), precisionField.takeUnless { it < OpenEhrField.DAYS } ?: OpenEhrField.DAYS, fieldStates, resolverStyle)

    override fun toExactTemporal(): TemporalAccessor =
        when {
            precisionField == OpenEhrField.YEARS && !isFieldMandatory(OpenEhrField.MONTHS) -> Year.from(this)
            precisionField == OpenEhrField.MONTHS && !isFieldMandatory(OpenEhrField.DAYS) -> YearMonth.from(this)
            precisionField == OpenEhrField.DAYS && !isFieldMandatory(OpenEhrField.HOURS) -> LocalDate.from(this)
            precisionField == OpenEhrField.NANOS -> dateTime
            else -> this
        }

    fun atOffset(offset: ZoneOffset): OpenEhrOffsetDateTime =
        OpenEhrOffsetDateTime.of(
                dateTime.year,
                dateTime.monthValue.takeIf { isStrictlySupportedUnit(ChronoUnit.MONTHS) },
                dateTime.dayOfMonth.takeIf { isStrictlySupportedUnit(ChronoUnit.DAYS) },
                dateTime.hour.takeIf { isStrictlySupportedUnit(ChronoUnit.HOURS) },
                dateTime.minute.takeIf { isStrictlySupportedUnit(ChronoUnit.MINUTES) },
                dateTime.second.takeIf { isStrictlySupportedUnit(ChronoUnit.SECONDS) },
                dateTime.nano.takeIf {
                    isStrictlySupportedUnit(ChronoUnit.NANOS) || isStrictlySupportedUnit(ChronoUnit.MICROS) || isStrictlySupportedUnit(ChronoUnit.MILLIS)
                },
                offset,
                fieldStates.plus(Pair(OpenEhrField.OFFSET_SECONDS, OpenEhrFieldState.OPTIONAL)),
                resolverStyle)

    override fun compareTo(other: OpenEhrLocalDateTime): Int = dateTime.compareTo(other.dateTime)
}