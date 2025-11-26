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
@XmlType(
    name = "DV_ORDERED", propOrder = [
        "normalRange",
        "otherReferenceRanges",
        "normalStatus"])
@XmlSeeAlso(value = [DvOrdinal::class, DvQuantified::class, DvScale::class])
@Serializable
@SerialName("DV_ORDERED")
@Open
abstract class DvOrdered() : DataValue() {

    companion object {
        @Suppress("unused")
        private const val serialVersionUID: Long = 0L
    }

    @XmlElement(name = "normal_range")
    @SerialName("normal_range")
    var normalRange: DvInterval? = null

    @XmlElement(name = "other_reference_ranges")
    @SerialName("other_reference_ranges")
    var otherReferenceRanges: MutableList<ReferenceRange> = mutableListOf()

    @XmlElement(name = "normal_status")
    @SerialName("normal_status")
    var normalStatus: CodePhrase? = null

    override fun visitProperties(ctx: care.better.platform.visitor.RmVisitorContext) {
        super.visitProperties(ctx)
        normalRange?.visit("normal_range", ctx)
        otherReferenceRanges.forEach { it.visit("other_reference_ranges", ctx) }
        normalStatus?.visit("normal_status", ctx)
    }

    override fun equals(other: Any?): Boolean =
        when {
            this === other -> true
            javaClass != other?.javaClass -> false
            (other as DvOrdered).normalRange != normalRange -> false
            else -> normalStatus == other.normalStatus
        }

    override fun hashCode(): Int = Objects.hash(normalRange, normalStatus)
}
