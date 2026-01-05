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
import java.util.*

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "DV_IDENTIFIER", propOrder = [
        "issuer",
        "assigner",
        "id",
        "type"]
)
@Serializable
@SerialName("DV_IDENTIFIER")
@Open
class DvIdentifier() : DataValue() {
    @JvmOverloads
    constructor(
        id: String,
        issuer: String? = null,
        assigner: String? = null,
        type: String? = null
    ) : this() {
        this.id = id
        this.issuer = issuer
        this.assigner = assigner
        this.type = type
    }

    companion object {
        @Suppress("unused")
        private const val serialVersionUID: Long = 0L
    }

    var issuer: String? = null

    var assigner: String? = null

    @XmlElement(required = true)
    @Required
    var id: String? = null

    var type: String? = null

    override fun equals(other: Any?): Boolean =
        when {
            this === other -> true
            javaClass != other?.javaClass -> false
            (other as DvIdentifier).id != id -> false
            other.assigner != assigner -> false
            other.issuer != issuer -> false
            else -> other.type == type
        }

    override fun hashCode(): Int = Objects.hash(id, type, issuer, assigner)

    override fun visit(attributeName: String, ctx: RmVisitorContext) {
        ctx.withObject(attributeName, this, "DV_IDENTIFIER") {
            visitProperties(ctx)
        }
    }

    override fun visitProperties(ctx: RmVisitorContext) {
        super.visitProperties(ctx)
        issuer?.let { ctx.visitValue("issuer", it, this) }
        assigner?.let { ctx.visitValue("assigner", it, this) }
        id?.let { ctx.visitValue("id", it, this) }
        type?.let { ctx.visitValue("type", it, this) }
    }
}
