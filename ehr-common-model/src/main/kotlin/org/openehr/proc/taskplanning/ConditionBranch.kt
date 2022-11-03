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
import org.openehr.rm.datatypes.DvText
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlType

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CONDITION_BRANCH", propOrder = ["test"])
@Open
class ConditionBranch : ChoiceBranch<PlanItem>, ExpressionNamesProvider {
    companion object {
        private const val serialVersionUID: Long = 0L
    }

    @XmlElement(required = true)
    @Required
    var test: BooleanContextExpression? = null

    constructor()

    constructor(description: DvText?, test: BooleanContextExpression?) : super(description) {
        this.test = test
    }

    override fun setWaitSpec(waitSpec: TaskWait?) {
        if (waitSpec != null) {
            throw UnsupportedOperationException("Wait Spec not supported on Condition Branches.")
        } else if (getWaitSpec() != null) {
            super.setWaitSpec(null)
        }
    }

    override fun accept(visitor: TaskModelVisitor) {
        val visited = visitor.visit(this)
        visitor.afterVisit(this)
        if (visited) {
            acceptRepeatAndWaitSpec(visitor)
            acceptReviewDataset(visitor)
            acceptExecutionRules(visitor)
            test?.also { it.accept(visitor) }
            acceptMembers(visitor)
        }
        visitor.afterAccept(this)
    }

    override fun getExpressionNames(): Sequence<String> = test?.name?.let { listOf(it).asSequence() } ?: emptySequence()

    override fun toString(): String =
        "ConditionBranch{" +
                "test=$test" + test +
                "} ${super.toString()}"
}
