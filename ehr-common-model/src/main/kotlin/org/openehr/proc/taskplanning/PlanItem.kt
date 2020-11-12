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

import care.better.platform.annotation.RequiresNotNull
import care.better.platform.proc.taskplanning.visitor.TaskModelVisitor
import care.better.platform.proc.taskplanning.visitor.VisitableByModelVisitor
import org.openehr.rm.common.Locatable
import org.openehr.rm.datastructures.ItemStructure
import org.openehr.rm.datatypes.DvText

/**
 * @author Primoz Delopst
 */

abstract class PlanItem : Locatable, VisitableByModelVisitor {

    @RequiresNotNull
    var description: DvText? = null
    var repeatSpec: TaskRepeat? = null
    var otherDetails: ItemStructure? = null
    private var waitSpec: TaskWait? = null
    var reviewDataset: MutableList<ReviewDatasetSpec> = mutableListOf()
    var classification: ItemStructure? = null
    var guidelineStep: String? = null
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

    open fun setWaitSpec(waitSpec: TaskWait?) {
        this.waitSpec = waitSpec
    }

    fun addReviewDataset(reviewDataset: ReviewDatasetSpec): PlanItem = this.reviewDataset.add(reviewDataset).let { this }

    override fun accept(visitor: TaskModelVisitor) {
        visitor.visit(this)
        visitor.afterVisit(this)
        acceptRepeatAndWaitSpec(visitor)
        acceptReviewDataset(visitor)
        visitor.afterAccept(this)
    }

    protected open fun acceptRepeatAndWaitSpec(taskModelVisitor: TaskModelVisitor) {
        repeatSpec?.also { }
        waitSpec?.also { }
    }

    protected open fun acceptReviewDataset(taskModelVisitor: TaskModelVisitor) {
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