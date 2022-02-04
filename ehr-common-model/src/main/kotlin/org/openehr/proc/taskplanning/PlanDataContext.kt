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

import care.better.openehr.rm.RmObject
import care.better.platform.annotation.Open
import care.better.platform.proc.taskplanning.visitor.TaskModelVisitor
import care.better.platform.proc.taskplanning.visitor.VisitableByModelVisitor
import java.io.Serializable
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlType

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "PLAN_DATA_CONTEXT", propOrder = [
        "variables",
        "expressions",
        "constants"])
@Open
class PlanDataContext : RmObject(), Serializable, VisitableByModelVisitor {
    companion object {
        private const val serialVersionUID: Long = 0L
    }

    var variables: MutableList<ContextVariable<*>> = mutableListOf()

    var expressions: MutableList<ContextExpression<*>> = mutableListOf()

    var constants: MutableList<ContextConstant<*>> = mutableListOf()

    override fun accept(visitor: TaskModelVisitor) {
        val visited = visitor.visit(this)
        visitor.afterVisit(this)
        if (visited) {
            variables.forEach { it.accept(visitor) }
            constants.forEach { it.accept(visitor) }
            expressions.forEach { it.accept(visitor) }
        }
        visitor.afterAccept(this)
    }

    override fun toString(): String =
        "PlanDataContext{" +
                "variables=$variables" +
                ", expressions=$expressions" +
                '}'
}
