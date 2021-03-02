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

import care.better.openehr.rm.RmObject
import care.better.platform.annotation.Open
import care.better.platform.annotation.Required
import org.openehr.base.basetypes.TerminologyId
import java.io.Serializable
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
    name = "CODE_PHRASE", propOrder = [
        "terminologyId",
        "codeString",
        "preferredTerm"])
@Open
class CodePhrase() : RmObject(), Serializable {
    @JvmOverloads
    constructor(terminologyId: TerminologyId, codeString: String, preferredTerm: String? = null) : this() {
        this.terminologyId = terminologyId
        this.codeString = codeString
        this.preferredTerm = preferredTerm
    }

    companion object {
        private const val serialVersionUID: Long = 0L

        /**
         * Creates a [CodePhrase] from terminology id and code
         *
         * @param terminology terminology id
         * @param code        code
         * @return [CodePhrase] object
         */
        @JvmStatic
        fun create(terminology: String, code: String): CodePhrase = CodePhrase(TerminologyId(terminology), code)

        /**
         * Gets language [CodePhrase]
         *
         * @param languageCode ISO language code (ISO_639-1)
         * @return [CodePhrase] object
         */
        @JvmStatic
        fun createLanguagePhrase(languageCode: String): CodePhrase = create("ISO_639-1", languageCode)

        /**
         * Gets territory [CodePhrase]
         *
         * @param territoryCode ISO territory code (ISO_3166-1)
         * @return [CodePhrase] object
         */
        @JvmStatic
        fun createTerritoryPhrase(territoryCode: String): CodePhrase = create("ISO_3166-1", territoryCode)

        /**
         * Gets encoding [CodePhrase]
         *
         * @param encodingCode encoding code (IANA character sets)
         * @return [CodePhrase] object
         */
        @JvmStatic
        fun createEncodingPhrase(encodingCode: String): CodePhrase = create("IANA_character-sets", encodingCode)
    }

    @XmlElement(name = "terminology_id", required = true)
    @Required
    var terminologyId: TerminologyId? = null

    @XmlElement(name = "code_string", required = true)
    @Required
    var codeString: String? = null

    @XmlElement(name = "preferred_term")
    var preferredTerm: String? = null

    override fun equals(other: Any?): Boolean =
        when {
            this === other -> true
            javaClass != other?.javaClass -> false
            (other as CodePhrase).terminologyId != terminologyId -> false
            else -> codeString == other.codeString
        }

    override fun hashCode(): Int = Objects.hash(terminologyId, codeString, preferredTerm)
}
