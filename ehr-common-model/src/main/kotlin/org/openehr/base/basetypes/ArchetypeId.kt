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

package org.openehr.base.basetypes

import care.better.platform.annotation.Open
import care.better.platform.visitor.RmVisitorContext
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlType

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ARCHETYPE_ID")
@Serializable
@SerialName("ARCHETYPE_ID")
@Open
class ArchetypeId() : ObjectId() {
    constructor(value: String) : this() {
        this.value = value
    }

    companion object {
        private const val serialVersionUID: Long = 0L
    }

    override fun visit(attributeName: String, ctx: RmVisitorContext) {
        if (ctx.beforeObject(attributeName, this, "ARCHETYPE_ID")) {
            value?.let { ctx.visitValue("value", it, this) }
            ctx.afterObject(attributeName, this, "ARCHETYPE_ID")
        }
    }
}
