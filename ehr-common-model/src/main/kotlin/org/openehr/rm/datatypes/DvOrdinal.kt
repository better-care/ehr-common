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
    name = "DV_ORDINAL", propOrder = [
        "value",
        "symbol"])
@Serializable
@SerialName("DV_ORDINAL")
@Open
class DvOrdinal() : DvOrdered() {
    @JvmOverloads
    constructor(
            value: Int,
            symbol: DvCodedText,
            normalRange: DvInterval? = null,
            otherReferenceRanges: MutableList<ReferenceRange> = mutableListOf(),
            normalStatus: CodePhrase? = null) : this() {
        this.value = value
        this.symbol = symbol
        this.normalRange = normalRange
        this.otherReferenceRanges = otherReferenceRanges
        this.normalStatus = normalStatus
    }

    companion object {
        private const val serialVersionUID: Long = 0L
    }

    var value: Int = 0

    @XmlElement(required = true)
    @Required
    var symbol: DvCodedText? = null

    override fun equals(other: Any?): Boolean =
        when {
            this === other -> true
            javaClass != other?.javaClass -> false
            !super.equals(other) -> false
            (other as DvOrdinal).value != value -> false
            else -> other.symbol == symbol
        }

    override fun hashCode(): Int = super.hashCode() + Objects.hash(value, symbol)

    override fun visit(attributeName: String, ctx: care.better.platform.visitor.RmVisitorContext) {
        normalRange?.visit("normal_range", ctx)
        otherReferenceRanges.forEach { it.visit("other_reference_ranges", ctx) }
        normalStatus?.visit("normal_status", ctx)
        ctx.visitValue("value", value)
        symbol?.visit("symbol", ctx)
        ctx.visitObject(attributeName, this, "DV_ORDINAL")
    }
}
