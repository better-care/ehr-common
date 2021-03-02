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
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlSeeAlso
import javax.xml.bind.annotation.XmlType

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */
@XmlType(name = "PERFORMABLE_ACTION", propOrder = [
    "resources",
    "otherParticipations"])
@XmlSeeAlso(value = [DefinedAction::class, SubPlan::class ])
@Open
abstract class PerformableAction : TaskAction {
    companion object {
        private const val serialVersionUID: Long = 0L
    }

    var resources: MutableList<ResourceParticipation> = mutableListOf()

    @XmlElement(name = "other_participations")
    var otherParticipations: MutableList<TaskParticipation> = mutableListOf()

    constructor()

    protected constructor(instructionActivity: LocatableRef?) : super(instructionActivity)

    protected constructor(instructionActivity: LocatableRef?, costingData: TaskCosting?) : super(instructionActivity, costingData)

    override fun accept(visitor: TaskModelVisitor) {
        val visited = visitor.visit(this)
        visitor.afterVisit(this)
        if (visited) {
            acceptPreconditions(visitor)
            acceptOtherParticipations(visitor)
            acceptResources(visitor)
        }
        visitor.afterAccept(this)
    }

    protected fun acceptOtherParticipations(visitor: TaskModelVisitor) {
        otherParticipations.forEach { it.accept(visitor) }
    }

    protected fun acceptResources(visitor: TaskModelVisitor) {
        resources.forEach { it.accept(visitor) }
    }

    override fun toString(): String =
        "PerformableAction{" +
                "resources=$resources" +
                ", otherParticipations=$otherParticipations" +
                "} ${super.toString()}"
}
