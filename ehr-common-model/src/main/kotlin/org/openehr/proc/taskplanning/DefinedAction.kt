/* Copyright 2020-2025 Better Ltd (www.better.care)
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

import care.better.platform.annotation.Opened
import care.better.platform.proc.taskplanning.visitor.TaskModelVisitor
import org.openehr.base.basetypes.LocatableRef
import org.openehr.rm.composition.Entry

/**
 * @author Primoz Delopst
 */

@Opened
class DefinedAction : PerformableAction {

    var prototype: MutableList<Entry> = mutableListOf()
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

    fun addPrototype(prototype: Entry): DefinedAction = this.prototype.add(prototype).let { this }

    override fun addResource(resource: ResourceParticipation): DefinedAction = super.addResource(resource) as DefinedAction

    override fun addOtherParticipation(participation: TaskParticipation): DefinedAction = super.addOtherParticipation(participation) as DefinedAction

    override fun addSubjectPrecondition(subjectPrecondition: SubjectPrecondition): DefinedAction = super.addSubjectPrecondition(subjectPrecondition) as DefinedAction

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