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
import java.io.Serializable
import javax.xml.bind.annotation.*

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StringDictionaryItem", propOrder = ["value"])
@kotlinx.serialization.Serializable
@SerialName("STRING_DICTIONARY_ITEM")
@Open
class StringDictionaryItem : RmObject(), Serializable {
    companion object {
        private const val serialVersionUID: Long = 0L
    }

    @XmlValue
    var value: String? = null

    @XmlAttribute(name = "id", required = true)
    @Required
    var id: String? = null

    override fun visit(attributeName: String, ctx: RmVisitorContext) {
        if (ctx.beforeObject(attributeName, this, "STRING_DICTIONARY_ITEM")) {
            value?.let { ctx.visitValue("value", it) }
            id?.let { ctx.visitValue("id", it) }
            ctx.afterObject(attributeName, this, "STRING_DICTIONARY_ITEM")
        }
    }
}
