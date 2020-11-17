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

import care.better.platform.annotation.Required
import care.better.platform.proc.taskplanning.visitor.TaskModelVisitor
import care.better.platform.proc.taskplanning.visitor.VisitableByModelVisitor
import org.openehr.rm.common.PartyProxy
import org.openehr.rm.composition.ContentItem
import org.openehr.rm.datastructures.ItemStructure
import org.openehr.rm.datatypes.DvIdentifier
import org.openehr.rm.datatypes.DvText
import org.openehr.rm.datatypes.DvUri

/**
 * @author Primoz Delopst
 */
class TaskPlan() : ContentItem(), VisitableByModelVisitor {

    var subject: PartyProxy? = null

    @Required
    var description: DvText? = null
    var guideline: ItemStructure? = null
    var principalPerformer: TaskParticipation? = null

    @Required
    var definition: TaskGroup<out PlanItem>? = null
    var executionHistory: TaskPlanExecutionHistory? = null
    var trainingLevel: Int? = null
    var bestPracticeRef: DvUri? = null
    var expiryTime: String? = null
    var dueTime: String? = null
    var orderSetType: DvIdentifier? = null
    var orderSetId: DvIdentifier? = null
    var indications: MutableList<DvText> = mutableListOf()

    constructor(description: DvText?, definition: TaskGroup<out PlanItem>?) : this() {
        this.description = description
        this.definition = definition
    }

    constructor(description: DvText?, principalPerformer: TaskParticipation?, definition: TaskGroup<out PlanItem>?) : this(description, definition) {
        this.principalPerformer = principalPerformer
    }

    fun addIndication(indication: DvText): TaskPlan = indications.add(indication).let { this }

    override fun accept(visitor: TaskModelVisitor) {
        visitor.visit(this)
        visitor.afterVisit(this)
        principalPerformer?.also { it.accept(visitor) }
        definition?.accept(visitor)
        visitor.afterAccept(this)
    }

    override fun toString(): String =
            "TaskPlan{" +
                    "subject=$subject" +
                    ", description=$description" +
                    ", guideline=$guideline" +
                    ", principalPerformer=$principalPerformer" +
                    ", definition=$definition" +
                    ", executionHistory=$executionHistory" +
                    ", trainingLevel=$trainingLevel" +
                    ", bestPracticeRef=$bestPracticeRef" +
                    ", expiryTime=$expiryTime" +
                    ", dueTime=$dueTime" +
                    ", orderSetType=$orderSetType" +
                    ", orderSetId=$orderSetId" +
                    ", indications=$indications" +
                    ", name=$name" +
                    ", uid=$uid" +
                    ", archetypeDetails=$archetypeDetails'" +
                    ", archetypeNodeId='$archetypeNodeId'" +
                    '}'
}