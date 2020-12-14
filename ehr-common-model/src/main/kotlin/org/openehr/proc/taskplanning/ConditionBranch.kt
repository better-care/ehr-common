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

/**
 * @author Primoz Delopst
 */

@Open
class ConditionBranch : ChoiceBranch<PlanItem>, ExpressionNamesProvider {

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
        visitor.visit(this)
        visitor.afterVisit(this)
        acceptRepeatAndWaitSpec(visitor)
        acceptReviewDataset(visitor)
        acceptExecutionRules(visitor)
        test?.also { it.accept(visitor) }
        acceptMembers(visitor)
        visitor.afterAccept(this)
    }

    override fun getExpressionNames(): Sequence<String> = test?.name?.let { listOf(it).asSequence() } ?: emptySequence()

    override fun toString(): String =
            "ConditionBranch{" +
                    "test=$test" + test +
                    "} ${super.toString()}"
}