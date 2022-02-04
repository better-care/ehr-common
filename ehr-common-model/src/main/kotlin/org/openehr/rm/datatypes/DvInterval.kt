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

import care.better.openehr.rm.RangeParameters
import care.better.platform.annotation.Open
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
@XmlType(
    name = "DV_INTERVAL", propOrder = [
        "lower",
        "upper",
        "lowerIncluded",
        "upperIncluded",
        "lowerUnbounded",
        "upperUnbounded"])
@Open
class DvInterval() : DataValue(), RangeParameters {
    @JvmOverloads
    constructor(
            lower: DvOrdered?,
            upper: DvOrdered?,
            lowerIncluded: Boolean? = null,
            upperIncluded: Boolean? = null,
            lowerUnbounded: Boolean = false,
            upperUnbounded: Boolean = false) : this() {
        this.lower = lower
        this.upper = upper
        this.lowerIncluded = lowerIncluded
        this.upperIncluded = upperIncluded
        this.lowerUnbounded = lowerUnbounded
        this.upperUnbounded = upperUnbounded
    }

    companion object {
        private const val serialVersionUID: Long = 0L
    }

    var lower: DvOrdered? = null

    var upper: DvOrdered? = null

    @XmlElement(name = "lower_included")
    var lowerIncluded: Boolean? = null

    @XmlElement(name = "upper_included")
    var upperIncluded: Boolean? = null

    @XmlElement(name = "lower_unbounded")
    var lowerUnbounded: Boolean = false

    @XmlElement(name = "upper_unbounded")
    var upperUnbounded: Boolean = false

    override fun isLowerIncluded(): Boolean? = lowerIncluded

    override fun isUpperIncluded(): Boolean? = upperIncluded

    override fun isLowerUnbounded(): Boolean = lowerUnbounded

    override fun isUpperUnbounded(): Boolean = upperUnbounded


    override fun equals(other: Any?): Boolean =
        when {
            this === other -> true
            javaClass != other?.javaClass -> false
            (other as DvInterval).lower != lower -> false
            other.upper != upper -> false
            other.lowerIncluded != lowerIncluded -> false
            other.upperIncluded != upperIncluded -> false
            other.lowerUnbounded != lowerUnbounded -> false
            else -> other.upperUnbounded == upperUnbounded
        }

    override fun hashCode(): Int = Objects.hash(lower, upper, lowerIncluded, upperIncluded, lowerUnbounded, upperUnbounded)
}
