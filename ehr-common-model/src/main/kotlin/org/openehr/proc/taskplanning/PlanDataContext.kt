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

import care.better.openehr.rm.RmObject
import care.better.platform.proc.taskplanning.visitor.TaskModelVisitor
import care.better.platform.proc.taskplanning.visitor.VisitableByModelVisitor
import java.io.Serializable

/**
 * @author Primoz Delopst
 */
class PlanDataContext : RmObject(), Serializable, VisitableByModelVisitor {

    var variables: MutableList<ContextVariable<*>> = mutableListOf()
    var expressions: MutableList<ContextExpression<*>> = mutableListOf()
    var constants: MutableList<ContextConstant<*>> = mutableListOf()

    fun addVariable(variable: ContextVariable<*>): PlanDataContext = variables.add(variable).let { this }

    fun addExpression(expression: ContextExpression<*>): PlanDataContext = expressions.add(expression).let { this }

    fun addConstant(constant: ContextConstant<*>): PlanDataContext = constants.add(constant).let { this }

    override fun accept(visitor: TaskModelVisitor) {
        visitor.visit(this)
        visitor.afterVisit(this)
        variables.forEach { it.accept(visitor) }
        constants.forEach { it.accept(visitor) }
        expressions.forEach { it.accept(visitor) }
        visitor.afterAccept(this)
    }

    override fun toString(): String =
         "PlanDataContext{" +
                "variables=$variables" +
                ", expressions=$expressions" +
                '}'
}