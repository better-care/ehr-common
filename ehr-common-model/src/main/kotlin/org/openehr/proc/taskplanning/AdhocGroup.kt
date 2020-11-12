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

import care.better.platform.proc.taskplanning.visitor.TaskModelVisitor
import org.openehr.rm.datatypes.DvText

/**
 * @author Primoz Delopst
 */

class AdhocGroup : ChoiceGroup<AdhocBranch> {
    constructor()

    constructor(description: DvText?) : super(description)

    constructor(description: DvText?, overrideType: OverrideType?) : this(description) {
        this.overrideType = overrideType
    }

    override fun addMember(member: AdhocBranch): AdhocGroup = super.addMember(member) as AdhocGroup

    override fun addExecutionRule(executionRule: ExecutionRule): AdhocGroup = super.addExecutionRule(executionRule) as AdhocGroup

    override fun accept(visitor: TaskModelVisitor) {
        visitor.visit(this)
        visitor.afterVisit(this)
        acceptRepeatAndWaitSpec(visitor)
        acceptReviewDataset(visitor)
        acceptExecutionRules(visitor)
        acceptMembers(visitor)
        visitor.afterAccept(this)
    }

    override fun toString(): String = "AdhocGroup{} ${super.toString()}"

}