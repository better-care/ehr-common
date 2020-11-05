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

package org.openehr.proc.taskplanning

import care.better.platform.annotation.Open
import care.better.platform.annotation.Required
import care.better.platform.proc.taskplanning.visitor.TaskModelVisitor
import care.better.platform.proc.taskplanning.visitor.VisitableByModelVisitor
import org.openehr.rm.common.Locatable
import org.openehr.rm.common.PartyProxy
import org.openehr.rm.datatypes.DvCodedText
import org.openehr.rm.datatypes.DvText
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlType

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */
@XmlType(name = "TASK_PARTICIPATION", propOrder = [
    "function",
    "role",
    "mode",
    "performer",
    "optionality"])
@Open
class TaskParticipation() : Locatable(), VisitableByModelVisitor {
    companion object {
        private const val serialVersionUID: Long = 0L
    }

    @XmlElement(required = true)
    @Required
    var function: DvText? = null

    @XmlElement
    var role: MutableList<DvText> = mutableListOf()

    @XmlElement
    var mode: DvCodedText? = null

    @XmlElement
    var performer: PartyProxy? = null

    @XmlElement(required = true)
    @Required
    var optionality: ValidityKind? = null

    constructor(function: DvText?, optionality: ValidityKind?) : this() {
        this.function = function
        this.optionality = optionality
    }

    override fun accept(visitor: TaskModelVisitor) {
        visitor.visit(this)
        visitor.afterVisit(this)
        visitor.afterAccept(this)
    }

    override fun toString(): String =
        "TaskParticipation{" +
                "function=$function" +
                ", role=$role" +
                ", mode=$mode" +
                ", performer=$performer" +
                ", optionality=$optionality" +
                ", name=$name" +
                ", uid=$uid" +
                ", archetypeDetails=$archetypeDetails" +
                ", archetypeNodeId='$archetypeNodeId'" +
                '}'
}
