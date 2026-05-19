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
import care.better.platform.visitor.RmVisitorContext
import jakarta.xml.bind.annotation.XmlAccessType
import jakarta.xml.bind.annotation.XmlAccessorType
import jakarta.xml.bind.annotation.XmlRootElement
import jakarta.xml.bind.annotation.XmlType
import kotlinx.serialization.SerialName
import java.io.Serializable

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */
@Open
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "REVISION_HISTORY", propOrder = ["items"])
@XmlRootElement(namespace = "http://schemas.openehr.org/v1")
@kotlinx.serialization.Serializable
@SerialName("REVISION_HISTORY")
class RevisionHistory : RmObject(), Serializable {
    companion object {
        @Suppress("unused")
        private const val serialVersionUID: Long = 0L
    }

    var items: MutableList<RevisionHistoryItem> = mutableListOf()

    override fun visit(attributeName: String, ctx: RmVisitorContext) {
        ctx.withObject(attributeName, this, "REVISION_HISTORY") {
            visitProperties(ctx)
        }
    }

    internal fun visitProperties(ctx: RmVisitorContext) {
        ctx.withCollection("items", items, this) {
            items.forEach { it.visit("items", ctx) }
        }
    }
}
