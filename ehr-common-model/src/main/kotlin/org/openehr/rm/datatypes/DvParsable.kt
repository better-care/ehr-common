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
    name = "DV_PARSABLE", propOrder = [
        "value",
        "formalism"])
@Serializable
@SerialName("DV_PARSABLE")
@Open
class DvParsable() : DvEncapsulated() {
    @JvmOverloads
    constructor(
            value: String,
            formalism: String,
            charset: CodePhrase? = null,
            language: CodePhrase? = null) : this() {
        this.value = value
        this.formalism = formalism
        this.charset = charset
        this.language = language
    }

    companion object {
        private const val serialVersionUID: Long = 0L
    }

    @XmlElement(required = true)
    @Required
    var value: String? = null

    @XmlElement(required = true)
    @Required
    var formalism: String? = null

    override fun equals(other: Any?): Boolean =
        when {
            this === other -> true
            javaClass != other?.javaClass -> false
            !super.equals(other) -> false
            (other as DvParsable).value != value -> false
            else -> formalism == other.formalism
        }

    override fun hashCode(): Int = super.hashCode() + Objects.hash(value, formalism)

    override fun visit(attributeName: String, ctx: RmVisitorContext) {
        if (ctx.beforeObject(attributeName, this, "DV_PARSABLE")) {
            charset?.visit("charset", ctx)
            language?.visit("language", ctx)
            value?.let { ctx.visitValue("value", it) }
            formalism?.let { ctx.visitValue("formalism", it) }
            ctx.afterObject(attributeName, this, "DV_PARSABLE")
        }
    }
}
