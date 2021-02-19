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
import care.better.platform.annotation.Required
import java.time.LocalTime
import java.time.OffsetTime
import java.time.format.DateTimeFormatter
import java.util.*
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlType

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DV_TIME", propOrder = ["value"])
@Open
class DvTime() : DvTemporal() {
    @JvmOverloads
    constructor(
            value: String,
            accuracy: DvDuration? = null,
            magnitudeStatus: String? = null,
            normalRange: DvInterval? = null,
            otherReferenceRanges: MutableList<ReferenceRange> = mutableListOf(),
            normalStatus: CodePhrase? = null) : this() {
        this.value = value
        this.accuracy = accuracy
        this.magnitudeStatus = magnitudeStatus
        this.normalRange = normalRange
        this.otherReferenceRanges = otherReferenceRanges
        this.normalStatus = normalStatus
    }

    companion object {
        private const val serialVersionUID: Long = 0L

        /**
         * Converts [LocalTime] to [DvTime]
         *
         * @param time [LocalTime]
         * @return [DvTime]
         */
        @JvmStatic
        fun create(time: LocalTime): DvTime = DvTime(DateTimeFormatter.ISO_LOCAL_TIME.format(time))

        /**
         * Converts [OffsetTime] to [DvTime]
         *
         * @param time [OffsetTime]
         * @return [DvTime]
         */
        @JvmStatic
        fun create(time: OffsetTime): DvTime = DvTime(DateTimeFormatter.ISO_OFFSET_TIME.format(time))
    }

    @XmlElement(required = true)
    @Required
    var value: String? = null

    override fun equals(other: Any?): Boolean =
        when {
            this === other -> true
            javaClass != other?.javaClass -> false
            !super.equals(other) -> false
            else -> (other as DvTime).value == value
        }

    override fun hashCode(): Int = super.hashCode() + Objects.hash(value)
}
