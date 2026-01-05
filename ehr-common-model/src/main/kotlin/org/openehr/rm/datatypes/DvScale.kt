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
import jakarta.xml.bind.annotation.XmlAccessType
import jakarta.xml.bind.annotation.XmlAccessorType
import jakarta.xml.bind.annotation.XmlElement
import jakarta.xml.bind.annotation.XmlType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "DV_SCALE", propOrder = [
        "symbol",
        "value"]
)
@Serializable
@SerialName("DV_SCALE")
@Open
class DvScale() : DvOrdered() {
    companion object {
        @Suppress("unused")
        private const val serialVersionUID: Long = 0L
    }

    @JvmOverloads
    constructor(
        value: Double,
        symbol: DvCodedText,
        normalRange: DvInterval? = null,
        otherReferenceRanges: MutableList<ReferenceRange> = mutableListOf(),
        normalStatus: CodePhrase? = null
    ) : this() {
        this.value = value
        this.symbol = symbol
        this.normalRange = normalRange
        this.otherReferenceRanges = otherReferenceRanges
        this.normalStatus = normalStatus
    }

    @XmlElement(required = true)
    @Required
    var symbol: DvCodedText? = null

    @XmlElement(required = true)
    @Required
    var value: Double = 0.0

    override fun visit(attributeName: String, ctx: RmVisitorContext) {
        ctx.withObject(attributeName, this, "DV_SCALE") {
            visitProperties(ctx)
        }
    }

    override fun visitProperties(ctx: RmVisitorContext) {
        super.visitProperties(ctx)
        symbol?.visit("symbol", ctx)
        ctx.visitValue("value", value, this)
    }
}
