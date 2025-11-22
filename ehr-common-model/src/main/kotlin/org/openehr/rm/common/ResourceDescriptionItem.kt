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
import care.better.platform.visitor.RmVisitorContext
import kotlinx.serialization.SerialName
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
    name = "RESOURCE_DESCRIPTION_ITEM", propOrder = [
        "language",
        "purpose",
        "keywords",
        "use",
        "misuse",
        "copyright",
        "originalResourceUri",
        "otherDetails"])
@kotlinx.serialization.Serializable
@SerialName("RESOURCE_DESCRIPTION_ITEM")
@Open
class ResourceDescriptionItem : RmObject(), Serializable {
    companion object {
        private const val serialVersionUID: Long = 0L
    }

    @XmlElement(required = true)
    @Required
    var language: CodePhrase? = null

    @XmlElement(required = true)
    @Required
    var purpose: String? = null

    var keywords: MutableList<String> = mutableListOf()

    var use: String? = null

    var misuse: String? = null

    var copyright: String? = null

    @XmlElement(name = "original_resource_uri")
    @SerialName("original_resource_uri")
    var originalResourceUri: MutableList<StringDictionaryItem> = mutableListOf()

    @XmlElement(name = "other_details")
    @SerialName("other_details")
    var otherDetails: MutableList<StringDictionaryItem> = mutableListOf()

    fun visit(attributeName: String, ctx: RmVisitorContext) {
        if (!ctx.visitObject(attributeName, this, "RESOURCE_DESCRIPTION_ITEM")) return
        language?.visit("language", ctx)
        purpose?.let { ctx.visitValue("purpose", it) }
        keywords.forEach { ctx.visitValue("keywords", it) }
        use?.let { ctx.visitValue("use", it) }
        misuse?.let { ctx.visitValue("misuse", it) }
        copyright?.let { ctx.visitValue("copyright", it) }
        originalResourceUri.forEach { it.visit("original_resource_uri", ctx) }
        otherDetails.forEach { it.visit("other_details", ctx) }
    }
}
