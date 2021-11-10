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

import java.time.DateTimeException
import java.time.format.ResolverStyle
import java.time.temporal.*
import java.util.*

/**
 * @author Matic Ribic
 */
abstract class OpenEhrTemporal<T : Temporal>(
        protected val temporal: T,
        val precisionField: OpenEhrField,
        fieldStates: Map<OpenEhrField, OpenEhrFieldState>,
        protected val resolverStyle: ResolverStyle) : OpenEhrFieldStateHolder(fieldStates), Temporal {

    companion object {

        @JvmStatic
        protected fun getDefaultFieldStates(fields: List<OpenEhrField>): Map<OpenEhrField, OpenEhrFieldState> =
            fields.asSequence().sortedDescending()
                .mapIndexed { i, field -> field to (OpenEhrFieldState.OPTIONAL.takeUnless { i == 0 } ?: OpenEhrFieldState.MANDATORY) }.toMap()
    }

    override fun isSupported(unit: TemporalUnit): Boolean =
        when {
            !temporal.isSupported(unit) -> false
            unit is ChronoUnit && unit.ordinal >= precisionField.unit.ordinal -> fieldStates.entries.firstOrNull { it.key.unit == unit }?.value
                .let { it?.possible == true }
            resolverStyle != ResolverStyle.STRICT -> fieldStates.entries.firstOrNull { it.key.unit == unit }?.value == OpenEhrFieldState.MANDATORY
            else -> false
        }

    override fun isSupported(field: TemporalField): Boolean =
        temporal.isSupported(field) && field is ChronoField
                && (if (field == ChronoField.OFFSET_SECONDS)
                        fieldStates[OpenEhrField.OFFSET_SECONDS] != OpenEhrFieldState.FORBIDDEN
                    else
                        isSupported(field.baseUnit as ChronoUnit))

    fun isStrictlySupported(openEhrField: OpenEhrField): Boolean = isStrictlySupportedUnit(openEhrField.unit)

    fun isStrictlySupportedUnit(unit: TemporalUnit): Boolean =
        temporal.isSupported(unit) && unit is ChronoUnit && unit.ordinal >= precisionField.unit.ordinal

    override fun getLong(field: TemporalField): Long = if (isSupported(field)) temporal.getLong(field) else throw DateTimeException("Unsupported field $field")

    override fun until(endExclusive: Temporal, unit: TemporalUnit): Long = temporal.until(endExclusive, unit)

    protected fun withTemporalField(field: TemporalField) =
        if (field is ChronoField)
            precisionField.takeUnless { (field.baseUnit as ChronoUnit).ordinal < it.unit.ordinal } ?: OpenEhrField.valueOf(field.baseUnit as ChronoUnit)
        else
            throw DateTimeException("Field $field must be instance of ChronoField (time value = $this)")

    fun withTemporalUnit(unit: TemporalUnit) =
        if (unit is ChronoUnit)
            precisionField.takeUnless { unit.ordinal < it.unit.ordinal } ?: OpenEhrField.valueOf(unit)
        else
            throw DateTimeException("Unit $unit must be instance of ChronoUnit (time value = $this)")

    abstract fun toExactTemporal(): TemporalAccessor

    override fun toString(): String {
        try {
            val builder = StringBuilder()

            OpenEhrField.DATE_FIELDS.asSequence().sortedDescending().filter { isSupported(it.field) }.forEach {
                if (it == OpenEhrField.YEARS)
                    builder.append(getLong(it.field))
                else
                    builder.append("-").append(getLong(it.field).toString().padStart(2, '0'))
            }

            OpenEhrField.TIME_FIELDS.asSequence().sortedDescending().filter { isSupported(it.field) }.forEach {
                if (it == OpenEhrField.HOURS) {
                    if (builder.isNotEmpty()) builder.append("T")
                    builder.append(getLong(it.field).toString().padStart(2, '0'))
                } else {
                    builder.append(":").append(getLong(it.field).toString().padStart(2, '0'))
                }
            }

            if (isSupported(OpenEhrField.OFFSET_SECONDS.field)) {
                builder.append(query(TemporalQueries.zone())).toString()
            }
            return builder.toString()
        } catch (e: Exception) {
            return "$temporal [OpenEhr with ${precisionField.toString().lowercase()} precision]"
        }
    }

    override fun equals(other: Any?): Boolean =
        when {
            this === other -> true
            javaClass != other?.javaClass -> false
            else -> temporal == (other as OpenEhrTemporal<*>).temporal && precisionField == other.precisionField
        }

    override fun hashCode(): Int = Objects.hash(temporal, precisionField, fieldStates, resolverStyle)
}
