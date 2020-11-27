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

package org.openehr.rm.datatypes

import care.better.platform.annotation.Opened
import care.better.platform.annotation.Required
import java.util.*

/**
 * @author Primoz Delopst
 */

@Opened
class DvQuantity : DvAmount() {

    companion object {
        /**
         * Creates [DvQuantity] from a magnitude (numeric value), unit string and precision. Precision can be null.
         *
         * @param magnitude magnitude
         * @param units     unit
         * @param precision precision
         * @return [DvQuantity] object
         */
        @JvmStatic
        fun create(magnitude: Double, units: String, precision: Int?): DvQuantity =
                DvQuantity().apply {
                    this.magnitude = magnitude
                    this.precision = precision
                    this.units = units
                }

        /**
         * Creates [DvQuantity] from a magnitude (numeric value) and unit string. Precision is not set.
         *
         * @param magnitude magnitude
         * @param units     unit
         * @return [DvQuantity] object
         */
        @JvmStatic
        fun create(magnitude: Double, units: String): DvQuantity = create(magnitude, units, null)
    }

    var magnitude: Double = 0.0

    @Required
    var units: String? = null
    var precision: Int? = null

    override fun equals(other: Any?): Boolean =
            when {
                this === other -> true
                javaClass != other?.javaClass -> false
                !super.equals(other) -> false
                (other as DvQuantity).magnitude != magnitude -> false
                other.units != units -> false
                else -> other.precision == precision
            }

    override fun hashCode(): Int = super.hashCode() + Objects.hash(magnitude, precision, units)
}