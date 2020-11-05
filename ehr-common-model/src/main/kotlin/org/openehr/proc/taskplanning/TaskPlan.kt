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
import org.openehr.rm.common.PartyProxy
import org.openehr.rm.composition.ContentItem
import org.openehr.rm.datastructures.ItemStructure
import org.openehr.rm.datatypes.DvIdentifier
import org.openehr.rm.datatypes.DvText
import org.openehr.rm.datatypes.DvUri
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlType

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */
@XmlType(name = "TASK_PLAN", propOrder = [
    "subject",
    "description",
    "principalPerformer",
    "definition",
    "trainingLevel",
    "guideline",
    "bestPracticeRef",
    "orderSetId",
    "orderSetType",
    "expiryTime",
    "dueTime",
    "indications",
    "executionHistory"])
@Open
class TaskPlan() : ContentItem(), VisitableByModelVisitor {
    companion object {
        private const val serialVersionUID: Long = 0L
    }

    @XmlElement
    var subject: PartyProxy? = null

    @XmlElement(required = true)
    @Required
    var description: DvText? = null

    @XmlElement
    var guideline: ItemStructure? = null

    @XmlElement(name = "principal_performer")
    var principalPerformer: TaskParticipation? = null

    @XmlElement(required = true)
    @Required
    var definition: TaskGroup<out PlanItem>? = null

    @XmlElement(name = "execution_history")
    var executionHistory: TaskPlanExecutionHistory? = null

    @XmlElement(name = "training_level")
    var trainingLevel: Int? = null

    @XmlElement(name = "best_practice_ref")
    var bestPracticeRef: DvUri? = null

    @XmlElement(name = "expiry_time")
    var expiryTime: String? = null

    @XmlElement(name = "due_time")
    var dueTime: String? = null

    @XmlElement(name = "order_set_type")
    var orderSetType: DvIdentifier? = null

    @XmlElement(name = "order_set_id")
    var orderSetId: DvIdentifier? = null

    @XmlElement(name = "indications")
    var indications: MutableList<DvText> = mutableListOf()

    constructor(description: DvText?, definition: TaskGroup<out PlanItem>?) : this() {
        this.description = description
        this.definition = definition
    }

    constructor(description: DvText?, principalPerformer: TaskParticipation?, definition: TaskGroup<out PlanItem>?) : this(description, definition) {
        this.principalPerformer = principalPerformer
    }

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
