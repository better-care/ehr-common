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
import kotlinx.serialization.Polymorphic
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.*
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlSeeAlso
import javax.xml.bind.annotation.XmlType

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DV_TEMPORAL", propOrder = ["accuracy"])
@XmlSeeAlso(value = [DvDateTime::class, DvTime::class, DvDate::class])
@Serializable
@SerialName("DV_TEMPORAL")
@Polymorphic
@Open
class DvTemporal : DvQuantified() {
    companion object {
        @Suppress("unused")
        private const val serialVersionUID: Long = 0L
    }

    var accuracy: DvDuration? = null

    override fun equals(other: Any?): Boolean =
        when {
            this === other -> true
            javaClass != other?.javaClass -> false
            !super.equals(other) -> false
            else -> (other as DvTemporal).accuracy == accuracy
        }

    override fun hashCode(): Int = super.hashCode() + Objects.hash(accuracy)

    override fun visit(attributeName: String, ctx: RmVisitorContext) {
        ctx.withObject(attributeName, this, "DV_TEMPORAL") {
            visitProperties(ctx)
        }
    }

    override fun visitProperties(ctx: RmVisitorContext) {
        super.visitProperties(ctx)
        accuracy?.visit("accuracy", ctx)
    }
}
