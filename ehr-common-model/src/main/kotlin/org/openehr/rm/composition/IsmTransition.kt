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

import care.better.openehr.rm.RmObject
import care.better.platform.annotation.Open
import care.better.platform.annotation.Required
import care.better.platform.visitor.RmVisitorContext
import kotlinx.serialization.SerialName
import org.openehr.rm.datatypes.DvCodedText
import org.openehr.rm.datatypes.DvText
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
    name = "ISM_TRANSITION", propOrder = [
        "currentState",
        "transition",
        "careflowStep",
        "reason"])
@kotlinx.serialization.Serializable
@SerialName("ISM_TRANSITION")
@Open
class IsmTransition : RmObject(), Serializable {
    companion object {
        private const val serialVersionUID: Long = 0L
    }

    @XmlElement(name = "current_state", required = true)
    @Required
    @SerialName("current_state")
    var currentState: DvCodedText? = null

    var transition: DvCodedText? = null

    @XmlElement(name = "careflow_step")
    @SerialName("careflow_step")
    var careflowStep: DvCodedText? = null

    var reason: MutableList<DvText> = mutableListOf()

    override fun visit(attributeName: String, ctx: RmVisitorContext) {
        if (ctx.beforeObject(attributeName, this, "ISM_TRANSITION")) {
            currentState?.visit("current_state", ctx)
            transition?.visit("transition", ctx)
            careflowStep?.visit("careflow_step", ctx)
            reason.forEach { it.visit("reason", ctx) }
            ctx.afterObject(attributeName, this, "ISM_TRANSITION")
        }
    }
}
