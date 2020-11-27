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
import care.better.platform.annotation.Required
import care.better.platform.proc.taskplanning.visitor.TaskModelVisitor
import org.openehr.rm.datatypes.DvText

/**
 * @author Primoz Delopst
 */

@Opened
abstract class Task<A : TaskAction> : PlanItem {

    @Required
    var action: A? = null
    var orderTags: MutableList<String> = mutableListOf()

    constructor()

    protected constructor(action: A?) {
        this.action = action
    }

    protected constructor(description: DvText?, action: A?) : super(description) {
        this.action = action
    }

    protected constructor(description: DvText?, repeatSpec: TaskRepeat?, waitSpec: TaskWait?, action: A?) : super(description, repeatSpec, waitSpec) {
        this.action = action
    }

    override fun accept(visitor: TaskModelVisitor) {
        visitor.visit(this)
        visitor.afterVisit(this)
        acceptRepeatAndWaitSpec(visitor)
        acceptReviewDataset(visitor)
        action?.accept(visitor)
        visitor.afterAccept(this)
    }

    override fun toString(): String =
            "Task{" +
                    "action=$action" +
                    ", orderTags=$orderTags" +
                    '}'
}