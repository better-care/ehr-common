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
import care.better.platform.proc.taskplanning.visitor.TaskModelVisitor
import org.openehr.base.basetypes.LocatableRef
import org.openehr.rm.composition.Entry
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlType

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */
@XmlType(
    name = "DEFINED_ACTION", propOrder = [
        "prototype",
        "optionality"
    ])
@Open
class DefinedAction : PerformableAction {
    companion object {
        private const val serialVersionUID: Long = 0L
    }

    @XmlElement(name = "prototype")
    var prototype: MutableList<Entry> = mutableListOf()

    @XmlElement(name = "optionality")
    var optionality: ValidityKind? = null

    constructor()

    constructor(optionality: ValidityKind?) {
        this.optionality = optionality
    }

    constructor(instructionActivity: LocatableRef?, optionality: ValidityKind?) : super(instructionActivity) {
        this.optionality = optionality
    }

    constructor(instructionActivity: LocatableRef?, costingData: TaskCosting?, optionality: ValidityKind?) : super(instructionActivity, costingData) {
        this.optionality = optionality
    }


    override fun accept(visitor: TaskModelVisitor) {
        visitor.visit(this)
        visitor.afterVisit(this)
        acceptPreconditions(visitor)
        acceptOtherParticipations(visitor)
        acceptResources(visitor)
        visitor.afterAccept(this)
    }

    override fun toString(): String =
        "DefinedAction{" +
                "prototype=$prototype" +
                ", optionality=$optionality" +
                "} ${super.toString()}"
}
