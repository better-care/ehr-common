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
import org.openehr.rm.common.PartyProxy
import org.openehr.rm.datatypes.DvCodedText
import org.openehr.rm.datatypes.DvText

/**
 * @author Primoz Delopst
 */
class TaskParticipation() : Locatable(), VisitableByModelVisitor {
    @RequiresNotNull
    var function: DvText? = null
    var role: MutableList<DvText> = mutableListOf()
    var mode: DvCodedText? = null
    var performer: PartyProxy? = null

    @RequiresNotNull
    var optionality: ValidityKind? = null

    constructor(function: DvText?, optionality: ValidityKind?) : this() {
        this.function = function
        this.optionality = optionality
    }

    fun addRole(role: DvText): TaskParticipation = this.role.add(role).let { this }

    override fun accept(visitor: TaskModelVisitor) {
        visitor.visit(this)
        visitor.afterVisit(this)
        visitor.afterAccept(this)
    }

    override fun toString(): String =
            "TaskParticipation{" +
                    "function=$function" +
                    ", role=$role" +
                    ", mode=$mode" +
                    ", performer=$performer" +
                    ", optionality=$optionality" +
                    ", name=$name" +
                    ", uid=$uid" +
                    ", archetypeDetails=$archetypeDetails" +
                    ", archetypeNodeId='$archetypeNodeId'" +
                    '}'
}