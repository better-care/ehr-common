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

/**
 * @author Primoz Delopst
 */

@Opened
abstract class PerformableAction : TaskAction {

    var resources: MutableList<ResourceParticipation> = mutableListOf()
    var otherParticipations: MutableList<TaskParticipation> = mutableListOf()

    constructor()

    protected constructor(instructionActivity: LocatableRef?) : super(instructionActivity)

    protected constructor(instructionActivity: LocatableRef?, costingData: TaskCosting?) : super(instructionActivity, costingData)

    open fun addResource(resource: ResourceParticipation): PerformableAction = resources.add(resource).let { this }

    open fun addOtherParticipation(participation: TaskParticipation): PerformableAction = otherParticipations.add(participation).let { this }

    override fun addSubjectPrecondition(subjectPrecondition: SubjectPrecondition): PerformableAction =
            super.addSubjectPrecondition(subjectPrecondition) as PerformableAction

    override fun accept(visitor: TaskModelVisitor) {
        visitor.visit(this)
        visitor.afterVisit(this)
        acceptPreconditions(visitor)
        acceptOtherParticipations(visitor)
        acceptResources(visitor)
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