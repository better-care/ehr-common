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
import org.openehr.rm.datastructures.ItemStructure
import org.openehr.rm.datatypes.DvText
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlSeeAlso
import javax.xml.bind.annotation.XmlType

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */
@XmlType(name = "PLAN_ITEM", propOrder = [
    "description",
    "repeatSpec",
    "waitSpec",
    "otherDetails",
    "reviewDataset",
    "classification",
    "guidelineStep",
    "reminders"])
@XmlSeeAlso(value = [TaskGroup::class, Task::class])
@Open
abstract class PlanItem : Locatable, VisitableByModelVisitor {
    companion object {
        private const val serialVersionUID: Long = 1L
    }

    @XmlElement(required = true)
    @Required
    var description: DvText? = null

    @XmlElement(name = "repeat_spec")
    var repeatSpec: TaskRepeat? = null

    @XmlElement(name = "other_details")
    var otherDetails: ItemStructure? = null

    @XmlElement(name = "wait_spec")
    private var waitSpec: TaskWait? = null

    @XmlElement(name = "review_dataset")
    var reviewDataset: MutableList<ReviewDatasetSpec> = mutableListOf()

    var classification: ItemStructure? = null

    @XmlElement(name = "guideline_step")
    var guidelineStep: String? = null

    @XmlElement(name = "reminders")
    var reminders: MutableList<Reminder> = mutableListOf()

    constructor()

    protected constructor(description: DvText?) {
        this.description = description
    }

    protected constructor(description: DvText?, repeatSpec: TaskRepeat?, waitSpec: TaskWait?) : this(description) {
        this.repeatSpec = repeatSpec
        this.waitSpec = waitSpec
    }

    fun getWaitSpec(): TaskWait? = waitSpec

    fun setWaitSpec(waitSpec: TaskWait?) {
        this.waitSpec = waitSpec
    }


    override fun accept(visitor: TaskModelVisitor) {
        visitor.visit(this)
        visitor.afterVisit(this)
        acceptRepeatAndWaitSpec(visitor)
        acceptReviewDataset(visitor)
        visitor.afterAccept(this)
    }

    protected fun acceptRepeatAndWaitSpec(taskModelVisitor: TaskModelVisitor) {
        repeatSpec?.also { }
        waitSpec?.also { }
    }

    protected fun acceptReviewDataset(taskModelVisitor: TaskModelVisitor) {
        reviewDataset.forEach { it.accept(taskModelVisitor) }
    }

    override fun toString(): String =
            "PlanItem{" +
                    "description=$description" +
                    ", repeatSpec=$repeatSpec" +
                    ", otherDetails=$otherDetails" +
                    ", waitSpec=$waitSpec" +
                    ", name=$name" +
                    ", uid=$uid" +
                    ", archetypeDetails=$archetypeDetails" +
                    ", archetypeNodeId='$archetypeNodeId" +
                    ", reviewDataset=$reviewDataset" +
                    ", classification=$classification" +
                    ", guidelineStep=$guidelineStep" +
                    ", reminders=$reminders" +
                    "}"
}
