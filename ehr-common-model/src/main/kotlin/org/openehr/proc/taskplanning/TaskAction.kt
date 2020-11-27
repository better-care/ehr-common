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
import care.better.platform.proc.taskplanning.visitor.VisitableByModelVisitor
import org.openehr.base.basetypes.LocatableRef
import org.openehr.rm.common.Locatable

/**
 * @author Primoz Delopst
 */

@Opened
abstract class TaskAction() : Locatable(), VisitableByModelVisitor {

    var instructionActivity: LocatableRef? = null
    var subjectPreconditions: MutableList<SubjectPrecondition> = mutableListOf()
    var costingData: TaskCosting? = null

    protected constructor(instructionActivity: LocatableRef?) : this() {
        this.instructionActivity = instructionActivity
    }

    protected constructor(instructionActivity: LocatableRef?, costingData: TaskCosting?) : this(instructionActivity) {
        this.costingData = costingData
    }

    open fun addSubjectPrecondition(subjectPrecondition: SubjectPrecondition): TaskAction = subjectPreconditions.add(subjectPrecondition).let { this }

    override fun accept(visitor: TaskModelVisitor) {
        visitor.visit(this)
        visitor.afterVisit(this)
        acceptPreconditions(visitor)
        visitor.afterAccept(this)
    }

    protected open fun acceptPreconditions(visitor: TaskModelVisitor) {
        subjectPreconditions.forEach { it.accept(visitor) }
    }

    override fun toString(): String =
            "TaskAction{" +
                ", instructionActivity=$instructionActivity" +
                ", subjectPreconditions=$subjectPreconditions" +
                ", costingData=$costingData" +
                ", name=$name" +
                ", uid=$uid" +
                ", archetypeDetails=$archetypeDetails" +
                ", archetypeNodeId='$archetypeNodeId'" +
                '}'
}