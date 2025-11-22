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
import care.better.platform.visitor.RmVisitorContext
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
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
    name = "DV_QUANTITY", propOrder = [
        "magnitude",
        "units",
        "precision",
        "unitsSystem",
        "unitsDisplayName"])
@Serializable
@SerialName("DV_QUANTITY")
@Open
class DvQuantity() : DvAmount() {
    @JvmOverloads
    constructor(
            magnitude: Double,
            units: String,
            precision: Int? = null,
            accuracy: Float? = null,
            accuracyIsPercent: Boolean? = null,
            magnitudeStatus: String? = null,
            normalRange: DvInterval? = null,
            otherReferenceRanges: MutableList<ReferenceRange> = mutableListOf(),
            normalStatus: CodePhrase? = null,
            unitsSystem: String? = null,
            unitsDisplayName: String? = null) : this() {
        this.magnitude = magnitude
        this.units = units
        this.precision = precision
        this.accuracy = accuracy
        this.accuracyIsPercent = accuracyIsPercent
        this.magnitudeStatus = magnitudeStatus
        this.normalRange = normalRange
        this.otherReferenceRanges = otherReferenceRanges
        this.normalStatus = normalStatus
        this.unitsSystem = unitsSystem
        this.unitsDisplayName = unitsDisplayName
    }

    companion object {
        private const val serialVersionUID: Long = 0L
    }

    var magnitude: Double = 0.0

    @XmlElement(required = true)
    @Required
    var units: String? = null

    @XmlElement(defaultValue = "-1")
    var precision: Int? = null

    @XmlElement(name = "units_system")
    @SerialName("units_system")
    var unitsSystem: String? = null

    @XmlElement(name = "units_display_name")
    @SerialName("units_display_name")
    var unitsDisplayName: String? = null

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

    override fun visit(attributeName: String, ctx: RmVisitorContext) {
        ctx.beforeObject(attributeName, this, "DV_QUANTITY")
        normalRange?.visit("normal_range", ctx)
        otherReferenceRanges.forEach { it.visit("other_reference_ranges", ctx) }
        normalStatus?.visit("normal_status", ctx)
        magnitudeStatus?.let { ctx.visitValue("magnitude_status", it) }
        accuracy?.let { ctx.visitValue("accuracy", it) }
        accuracyIsPercent?.let { ctx.visitValue("accuracy_is_percent", it) }
        ctx.visitValue("magnitude", magnitude)
        units?.let { ctx.visitValue("units", it) }
        precision?.let { ctx.visitValue("precision", it) }
        unitsSystem?.let { ctx.visitValue("units_system", it) }
        unitsDisplayName?.let { ctx.visitValue("units_display_name", it) }
        ctx.afterObject(attributeName, this, "DV_QUANTITY")
    }
}
