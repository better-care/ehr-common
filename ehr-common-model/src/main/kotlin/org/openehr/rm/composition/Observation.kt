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

package org.openehr.rm.composition

import care.better.platform.annotation.Open
import care.better.platform.annotation.Required
import care.better.platform.visitor.RmVisitorContext
import jakarta.xml.bind.annotation.XmlAccessType
import jakarta.xml.bind.annotation.XmlAccessorType
import jakarta.xml.bind.annotation.XmlElement
import jakarta.xml.bind.annotation.XmlType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.openehr.rm.datastructures.History

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "OBSERVATION", propOrder = [
        "data",
        "state"]
)
@Serializable
@SerialName("OBSERVATION")
@Open
class Observation : CareEntry() {
    companion object {
        @Suppress("unused")
        private const val serialVersionUID: Long = 0L
    }

    @XmlElement(required = true)
    @Required
    var data: History? = null

    var state: History? = null

    override fun visit(attributeName: String, ctx: RmVisitorContext) {
        ctx.withLocatable(attributeName, this, "OBSERVATION") {
            visitProperties(ctx)
        }
    }

    override fun visitProperties(ctx: RmVisitorContext) {
        super.visitProperties(ctx)
        data?.visit("data", ctx)
        state?.visit("state", ctx)
    }
}
