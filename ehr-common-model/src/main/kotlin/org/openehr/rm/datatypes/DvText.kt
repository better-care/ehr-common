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
import jakarta.xml.bind.annotation.*
import kotlinx.serialization.Polymorphic
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.*

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "DV_TEXT", propOrder = [
        "value",
        "hyperlink",
        "formatting",
        "mappings",
        "language",
        "encoding"]
)
@XmlSeeAlso(DvCodedText::class)
@Serializable
@SerialName("DV_TEXT")
@Polymorphic
@Open
class DvText() : DataValue() {
    companion object {
        @Suppress("unused")
        private const val serialVersionUID: Long = 0L
    }

    @JvmOverloads
    constructor(
        value: String,
        hyperlink: DvUri? = null,
        formatting: String? = null,
        mappings: MutableList<TermMapping> = mutableListOf(),
        language: CodePhrase? = null,
        encoding: CodePhrase? = null
    ) : this() {
        this.value = value
        this.hyperlink = hyperlink
        this.formatting = formatting
        this.mappings = mappings
        this.language = language
        this.encoding = encoding
    }

    @XmlElement(required = true)
    @Required
    var value: String? = null

    var hyperlink: DvUri? = null

    var formatting: String? = null

    var mappings: MutableList<TermMapping> = mutableListOf()

    var language: CodePhrase? = null

    var encoding: CodePhrase? = null

    override fun equals(other: Any?): Boolean =
        when {
            this === other -> true
            javaClass != other?.javaClass -> false
            (other as DvText).value != value -> false
            other.hyperlink != hyperlink -> false
            other.formatting != formatting -> false
            other.language != language -> false
            else -> other.encoding == encoding
        }

    override fun hashCode(): Int = Objects.hash(value, encoding, formatting, hyperlink, language)

    override fun visit(attributeName: String, ctx: RmVisitorContext) {
        ctx.withObject(attributeName, this, "DV_TEXT") {
            visitProperties(ctx)
        }
    }

    override fun visitProperties(ctx: RmVisitorContext) {
        super.visitProperties(ctx)
        value?.let { ctx.visitValue("value", it, this) }
        hyperlink?.visit("hyperlink", ctx)
        formatting?.let { ctx.visitValue("formatting", it, this) }
        ctx.withCollection("mappings", mappings, this) {
            mappings.forEach { it.visit("mappings", ctx) }
        }
        language?.visit("language", ctx)
        encoding?.visit("encoding", ctx)
    }
}
