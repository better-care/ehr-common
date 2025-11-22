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
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.*
import javax.xml.bind.annotation.*

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DV_QUANTIFIED", propOrder = ["magnitudeStatus"])
@XmlSeeAlso(value = [DvTemporal::class, DvAmount::class])
@Serializable
@SerialName("DV_QUANTIFIED")
@Open
abstract class DvQuantified : DvOrdered() {

    companion object {
        private const val serialVersionUID: Long = 0L
    }

    @XmlElement(name = "magnitude_status")
    @SerialName("magnitude_status")
    var magnitudeStatus: String? = null

    override fun equals(other: Any?): Boolean =
        when {
            this === other -> true
            javaClass != other?.javaClass -> false
            !super.equals(other) -> false
            else -> (other as DvQuantified).magnitudeStatus == magnitudeStatus
        }

    override fun hashCode(): Int = super.hashCode() + Objects.hash(magnitudeStatus)

    open override fun visit(attributeName: String, ctx: care.better.platform.visitor.RmVisitorContext) {
        normalRange?.visit("normal_range", ctx)
        otherReferenceRanges.forEach { it.visit("other_reference_ranges", ctx) }
        normalStatus?.visit("normal_status", ctx)
        magnitudeStatus?.let { ctx.visitValue("magnitude_status", it) }
        ctx.visitObject(attributeName, this, "DV_QUANTIFIED")
    }
}
