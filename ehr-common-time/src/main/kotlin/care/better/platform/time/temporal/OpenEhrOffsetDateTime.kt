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
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.ResolverStyle
import java.time.temporal.Temporal
import java.time.temporal.TemporalAccessor
import java.time.temporal.TemporalField
import java.time.temporal.TemporalUnit

/**
 * @author Matic Ribic
 */
class OpenEhrOffsetDateTime(
        dateTime: OffsetDateTime,
        precisionField: OpenEhrField,
        fieldStates: Map<OpenEhrField, OpenEhrFieldState>,
        resolverStyle: ResolverStyle) :
    OpenEhrTemporal<OffsetDateTime>(dateTime, precisionField, fieldStates, resolverStyle), Comparable<OpenEhrOffsetDateTime>, Serializable {

    companion object {

        @JvmStatic
        fun now(): OpenEhrOffsetDateTime = now(Clock.systemDefaultZone())

        @JvmStatic
        fun now(clock: Clock): OpenEhrOffsetDateTime =
            OpenEhrOffsetDateTime(OffsetDateTime.now(clock), OpenEhrField.MINIMUM, getDefaultFieldStates(OpenEhrField.DATE_TIME_FIELDS), ResolverStyle.SMART)

        @JvmStatic
        @JvmOverloads
        fun of(
                year: Int,
                month: Int? = null,
                dayOfMonth: Int? = null,
                hour: Int? = null,
                minute: Int? = null,
                second: Int? = null,
                nano: Int? = null,
                offset: ZoneOffset): OpenEhrOffsetDateTime =
            of(year, month, dayOfMonth, hour, minute, second, nano, offset, getDefaultFieldStates(OpenEhrField.DATE_TIME_FIELDS))

        @JvmStatic
        fun of(
                year: Int,
                month: Int? = null,
                dayOfMonth: Int? = null,
                hour: Int? = null,
                minute: Int? = null,
                second: Int? = null,
                nano: Int? = null,
                offset: ZoneOffset,
                fieldStates: Map<OpenEhrField, OpenEhrFieldState>,
                resolverStyle: ResolverStyle = ResolverStyle.SMART): OpenEhrOffsetDateTime =
            OpenEhrOffsetDateTime(
                    OffsetDateTime.of(year, month ?: 1, dayOfMonth ?: 1, hour ?: 0, minute ?: 0, second ?: 0, nano ?: 0, offset),
                    (OpenEhrLocalTime.getPrecision(hour, minute, second, nano) ?: OpenEhrLocalDate.getPrecision(year, month, dayOfMonth)),
                    fieldStates,
                    resolverStyle)

        @JvmStatic
        fun from(temporal: TemporalAccessor, precisionField: OpenEhrField, context: OpenEhrDateTimeFormatterContext): OpenEhrOffsetDateTime {
            if (temporal is OpenEhrOffsetDateTime) {
                return temporal
            }
            requireNotNull(ZoneOffset.from(temporal), { "temporal" })

            return OpenEhrOffsetDateTime(OffsetDateTime.from(temporal), precisionField, context.patternFieldStates, context.resolverStyle)
        }
    }

    fun toLocalDateTime(): OpenEhrLocalDateTime = OpenEhrLocalDateTime(dateTime.toLocalDateTime(), precisionField, fieldStates, resolverStyle)

    fun toOffsetTime(): OpenEhrOffsetTime = OpenEhrOffsetTime(dateTime.toOffsetTime(), precisionField, fieldStates, resolverStyle)

    fun withOffsetSameInstant(offset: ZoneOffset): OpenEhrOffsetDateTime {
        if (dateTime.offset == offset) {
            return this
        }

        return dateTime.withOffsetSameInstant(offset).toLocalDateTime().let {
            OpenEhrOffsetDateTime(OffsetDateTime.of(it, offset), precisionField, fieldStates, resolverStyle)
        }
    }

    val dateTime: OffsetDateTime = temporal

    override fun with(field: TemporalField, newValue: Long): Temporal =
        OpenEhrOffsetDateTime(dateTime.with(field, newValue), withTemporalField(field), fieldStates, resolverStyle)

    override fun plus(amountToAdd: Long, unit: TemporalUnit): Temporal =
        OpenEhrOffsetDateTime(dateTime.plus(amountToAdd, unit), withTemporalUnit(unit), fieldStates, resolverStyle)

    override fun toExactTemporal(): TemporalAccessor = this.takeUnless { precisionField == OpenEhrField.NANOS } ?: dateTime

    override fun compareTo(other: OpenEhrOffsetDateTime): Int = dateTime.compareTo(other.dateTime)
}
