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
import kotlinx.serialization.Polymorphic
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.*
import javax.xml.bind.annotation.*

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DV_URI", propOrder = ["value"])
@XmlSeeAlso(DvUri::class, DvEhrUri::class)
@Serializable
@SerialName("DV_URI")
@Polymorphic
@Open
class DvUri() : DataValue() {
    constructor(value: String) : this() {
        this.value = value
    }

    companion object {
        private const val serialVersionUID: Long = 0L
    }

    @XmlElement(required = true)
    @XmlSchemaType(name = "anyURI")
    @Required
    var value: String? = null

    override fun equals(other: Any?): Boolean =
        when {
            this === other -> true
            javaClass != other?.javaClass -> false
            else -> (other as DvUri).value == value
        }

    override fun hashCode(): Int = Objects.hash(value)

    open override fun visit(attributeName: String, ctx: RmVisitorContext) {
        if (!ctx.visitObject(attributeName, this, "DV_URI")) return
        value?.let { ctx.visitValue("value", it) }
    }
}
