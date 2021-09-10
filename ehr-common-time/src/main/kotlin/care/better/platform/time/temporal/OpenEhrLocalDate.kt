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
import java.time.Clock
import java.time.LocalDate
import java.time.Year
import java.time.YearMonth
import java.time.format.ResolverStyle
import java.time.temporal.Temporal
import java.time.temporal.TemporalAccessor
import java.time.temporal.TemporalField
import java.time.temporal.TemporalUnit

/**
 * @author Matic Ribic
 */
class OpenEhrLocalDate(date: LocalDate, precisionField: OpenEhrField, fieldStates: Map<OpenEhrField, OpenEhrFieldState>, resolverStyle: ResolverStyle) :
    OpenEhrTemporal<LocalDate>(date, precisionField, fieldStates, resolverStyle), Comparable<OpenEhrLocalDate>, Serializable {

    companion object {

        @JvmStatic
        fun now(): OpenEhrLocalDate = now(Clock.systemDefaultZone())

        @JvmStatic
        fun now(clock: Clock): OpenEhrLocalDate =
            OpenEhrLocalDate(LocalDate.now(clock), OpenEhrField.MINIMUM, getDefaultFieldStates(OpenEhrField.DATE_FIELDS), ResolverStyle.SMART)

        @JvmStatic
        @JvmOverloads
        fun of(year: Int, month: Int? = null, dayOfMonth: Int? = null): OpenEhrLocalDate =
            of(year, month, dayOfMonth, getDefaultFieldStates(OpenEhrField.DATE_FIELDS))

        @JvmStatic
        fun of(
                year: Int,
                month: Int? = null,
                dayOfMonth: Int? = null,
                fieldStates: Map<OpenEhrField, OpenEhrFieldState>,
                resolverStyle: ResolverStyle = ResolverStyle.SMART): OpenEhrLocalDate =
            OpenEhrLocalDate(LocalDate.of(year, month ?: 1, dayOfMonth ?: 1), getPrecision(year, month, dayOfMonth), fieldStates, resolverStyle)

        @JvmStatic
        fun getPrecision(year: Int, month: Int?, dayOfMonth: Int?): OpenEhrField =
            when {
                month == null -> OpenEhrField.YEARS
                dayOfMonth == null -> OpenEhrField.MONTHS
                else -> OpenEhrField.DAYS
            }

        @JvmStatic
        fun from(temporal: TemporalAccessor, precisionField: OpenEhrField, context: OpenEhrDateTimeFormatterContext): OpenEhrLocalDate {
            if (temporal is OpenEhrLocalDate) {
                return temporal
            }

            return when (precisionField) {
                OpenEhrField.YEARS -> Year.from(temporal).atDay(1)
                OpenEhrField.MONTHS -> YearMonth.from(temporal).atDay(1)
                else -> LocalDate.from(temporal)
            }.let {
                OpenEhrLocalDate(
                        it,
                        precisionField.takeUnless { field -> field < OpenEhrField.DAYS } ?: OpenEhrField.DAYS,
                        context.patternFieldStates, context.resolverStyle)
            }
        }
    }

    val date: LocalDate = temporal

    override fun with(field: TemporalField, newValue: Long): Temporal =
        OpenEhrLocalDate(
                date.with(field, newValue),
                withTemporalField(field).takeUnless { it < OpenEhrField.DAYS } ?: OpenEhrField.DAYS,
                fieldStates,
                resolverStyle)

    override fun plus(amountToAdd: Long, unit: TemporalUnit): Temporal =
        OpenEhrLocalDate(
                date.plus(amountToAdd, unit),
                withTemporalUnit(unit).takeUnless { it < OpenEhrField.DAYS } ?: OpenEhrField.DAYS,
                fieldStates,
                resolverStyle)

    override fun toExactTemporal(): TemporalAccessor =
        when {
            precisionField == OpenEhrField.YEARS && !isFieldMandatory(OpenEhrField.MONTHS) -> Year.from(this)
            precisionField == OpenEhrField.MONTHS && !isFieldMandatory(OpenEhrField.DAYS) -> YearMonth.from(this)
            precisionField == OpenEhrField.DAYS && !isFieldMandatory(OpenEhrField.HOURS) -> LocalDate.from(this)
            else -> this
        }

    override fun compareTo(other: OpenEhrLocalDate): Int = date.compareTo(other.date)
}