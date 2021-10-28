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

import java.time.temporal.ChronoField
import java.time.temporal.ChronoUnit

/**
 * @author Matic Ribic
 */
enum class OpenEhrField(val field: ChronoField) {
    OFFSET_SECONDS(ChronoField.OFFSET_SECONDS),
    NANOS(ChronoField.NANO_OF_SECOND),
    SECONDS(ChronoField.SECOND_OF_MINUTE),
    MINUTES(ChronoField.MINUTE_OF_HOUR),
    HOURS(ChronoField.HOUR_OF_DAY),
    DAYS(ChronoField.DAY_OF_MONTH),
    MONTHS(ChronoField.MONTH_OF_YEAR),
    YEARS(ChronoField.YEAR);

    val unit: ChronoUnit = field.baseUnit as ChronoUnit

    companion object {
        @JvmField
        val MINIMUM = NANOS
        @JvmField
        val DATE_TIME_FIELDS = values().filter { it != OFFSET_SECONDS }
        @JvmField
        val DATE_FIELDS = listOf(YEARS, MONTHS, DAYS)
        @JvmField
        val TIME_FIELDS = DATE_TIME_FIELDS.filter { !DATE_FIELDS.contains(it) }

        private val FIELDS_BY_CHRONO_UNITS: Map<ChronoUnit, OpenEhrField> = ChronoUnit.values().asSequence().map { unit ->
            unit to values().asSequence().filter { it != OFFSET_SECONDS }.firstOrNull { unit.ordinal <= it.unit.ordinal }
        }.filter { it.second != null }.map { it.first to it.second!! }.toMap()

        private val FIELDS_BY_CHRONO_FIELDS: Map<ChronoField, OpenEhrField> = values().map { it.field to it }.toMap()

        @JvmStatic
        fun valueOf(unit: ChronoUnit): OpenEhrField = FIELDS_BY_CHRONO_UNITS.get(unit)
            ?: throw IllegalArgumentException("Missing OpenEhrField for ChronoUnit $unit")

        @JvmStatic
        fun valueOf(field: ChronoField): OpenEhrField =
            FIELDS_BY_CHRONO_FIELDS.get(field) ?: throw IllegalArgumentException("Missing OpenEhrField for ChronoField $field")
    }
}
