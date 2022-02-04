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

package org.openehr.base.foundationtypes

import care.better.openehr.rm.RangeParameters
import care.better.openehr.rm.RmObject
import care.better.platform.annotation.Open
import java.io.Serializable
import javax.xml.bind.annotation.*

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "Interval", propOrder = [
        "lowerIncluded",
        "upperIncluded",
        "lowerUnbounded",
        "upperUnbounded"])
@XmlSeeAlso(value = [IntervalOfInteger::class, IntervalOfReal::class, IntervalOfDate::class, IntervalOfDateTime::class, IntervalOfTime::class, IntervalOfDuration::class])
@Open
abstract class Interval : RmObject(), Serializable, RangeParameters {
    companion object {
        private const val serialVersionUID: Long = 0L
    }

    @XmlElement(name = "lower_included")
    var lowerIncluded: Boolean? = null

    @XmlElement(name = "upper_included")
    var upperIncluded: Boolean? = null

    @XmlElement(name = "lower_unbounded")
    var lowerUnbounded = false

    @XmlElement(name = "upper_unbounded")
    var upperUnbounded = false

    override fun isLowerIncluded(): Boolean? = lowerIncluded

    override fun isUpperIncluded(): Boolean? = upperIncluded

    override fun isLowerUnbounded(): Boolean = lowerUnbounded

    override fun isUpperUnbounded(): Boolean = upperUnbounded
}
