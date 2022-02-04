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

package org.openehr.rm.common

import care.better.openehr.rm.RmObject
import care.better.platform.annotation.Open
import care.better.platform.annotation.Required
import org.openehr.base.resource.TranslationDetails
import org.openehr.rm.datatypes.CodePhrase
import java.io.Serializable
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
    name = "AUTHORED_RESOURCE", propOrder = [
        "originalLanguage",
        "isControlled",
        "description",
        "translations",
        "revisionHistory"])
@Open
abstract class AuthoredResource() : RmObject(), Serializable {
    companion object {
        private const val serialVersionUID: Long = 0L
    }

    @JvmOverloads
    constructor(
            originalLanguage: CodePhrase,
            isControlled: Boolean? = null,
            description: ResourceDescription? = null,
            translations: MutableList<TranslationDetails> = mutableListOf(),
            revisionHistory: RevisionHistory? = null) : this() {
        this.originalLanguage = originalLanguage
        this.isControlled = isControlled
        this.description = description
        this.translations = translations
        this.revisionHistory = revisionHistory
    }

    @XmlElement(name = "original_language", required = true)
    @Required
    var originalLanguage: CodePhrase? = null

    @XmlElement(name = "is_controlled")
    var isControlled: Boolean? = null

    var description: ResourceDescription? = null

    var translations: MutableList<TranslationDetails> = mutableListOf()

    @XmlElement(name = "revision_history")
    var revisionHistory: RevisionHistory? = null
}
