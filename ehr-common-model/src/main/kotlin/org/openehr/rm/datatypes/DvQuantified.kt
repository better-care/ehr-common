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

package org.openehr.rm.datatypes

import care.better.platform.annotation.Open
import java.util.*
import javax.xml.bind.annotation.*

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DV_QUANTIFIED", propOrder = ["magnitudeStatus"])
@XmlSeeAlso(value = [DvTemporal::class, DvAmount::class])
@Open
abstract class DvQuantified : DvOrdered() {

    companion object {
        private const val serialVersionUID: Long = 0L
    }

    @XmlElement(name = "magnitude_status")
    var magnitudeStatus: String? = null

    override fun equals(other: Any?): Boolean =
        when {
            this === other -> true
            javaClass != other?.javaClass -> false
            !super.equals(other) -> false
            else -> (other as DvQuantified).magnitudeStatus == magnitudeStatus
        }

    override fun hashCode(): Int = super.hashCode() + Objects.hash(magnitudeStatus)
}
