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
import java.util.*
import javax.xml.bind.annotation.*

/**
 * @author Primoz Delopst
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
@Open
class DvText
@JvmOverloads
constructor(
    @XmlElement(required = true)
    @Required
    var value: String? = null,
    var hyperlink: DvUri? = null,
    var formatting: String? = null,
    var mappings: MutableList<TermMapping> = mutableListOf(),
    var language: CodePhrase? = null,
    var encoding: CodePhrase? = null
) : DataValue() {

    companion object {
        private const val serialVersionUID: Long = 0L

        /**
         * Creates a [DvText] from a string
         *
         * @param value input value
         * @return [DvText] object
         */
        @JvmStatic
        fun create(value: String?): DvText = DvText(value)
    }

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
}
