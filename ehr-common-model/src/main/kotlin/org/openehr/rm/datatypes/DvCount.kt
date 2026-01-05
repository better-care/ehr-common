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
import care.better.platform.visitor.RmVisitorContext
import jakarta.xml.bind.annotation.XmlAccessType
import jakarta.xml.bind.annotation.XmlAccessorType
import jakarta.xml.bind.annotation.XmlType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.*

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DV_COUNT", propOrder = ["magnitude"])
@Serializable
@SerialName("DV_COUNT")
@Open
class DvCount() : DvAmount() {
    @JvmOverloads
    constructor(
        magnitude: Long,
        accuracy: Float? = null,
        accuracyIsPercent: Boolean? = null,
        magnitudeStatus: String? = null,
        normalRange: DvInterval? = null,
        otherReferenceRanges: MutableList<ReferenceRange> = mutableListOf(),
        normalStatus: CodePhrase? = null,
    ) : this() {
        this.magnitude = magnitude
        this.accuracy = accuracy
        this.accuracyIsPercent = accuracyIsPercent
        this.magnitudeStatus = magnitudeStatus
        this.normalRange = normalRange
        this.otherReferenceRanges = otherReferenceRanges
        this.normalStatus = normalStatus
    }

    companion object {
        @Suppress("unused")
        private const val serialVersionUID: Long = 0L
    }

    var magnitude: Long = 0L

    override fun equals(other: Any?): Boolean =
        when {
            this === other -> true
            javaClass != other?.javaClass -> false
            !super.equals(other) -> false
            else -> (other as DvCount).magnitude == magnitude
        }

    override fun hashCode(): Int = super.hashCode() + Objects.hash(magnitude)

    override fun visit(attributeName: String, ctx: RmVisitorContext) {
        ctx.withObject(attributeName, this, "DV_COUNT") {
            visitProperties(ctx)
        }
    }

    override fun visitProperties(ctx: RmVisitorContext) {
        super.visitProperties(ctx)
        ctx.visitValue("magnitude", magnitude, this)
    }
}
