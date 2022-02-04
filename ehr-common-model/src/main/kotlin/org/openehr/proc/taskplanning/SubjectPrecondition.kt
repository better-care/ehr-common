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
import care.better.platform.annotation.Required
import care.better.platform.proc.taskplanning.visitor.TaskModelVisitor
import care.better.platform.proc.taskplanning.visitor.VisitableByModelVisitor
import java.io.Serializable
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlType

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "SUBJECT_PRECONDITION", propOrder = [
        "description",
        "expression"])
@Open
class SubjectPrecondition() : RmObject(), Serializable, VisitableByModelVisitor, ExpressionNamesProvider {
    companion object {
        private const val serialVersionUID: Long = 0L
    }

    @XmlElement(required = true)
    @Required
    var description: String? = null

    var expression: BooleanContextExpression? = null

    constructor(description: String?) : this() {
        this.description = description
    }

    constructor(description: String?, expression: BooleanContextExpression?) : this(description) {
        this.expression = expression
    }

    override fun accept(visitor: TaskModelVisitor) {
        val visited = visitor.visit(this)
        visitor.afterVisit(this)
        if (visited) {
            expression?.also { it.accept(visitor) }
        }
        visitor.afterAccept(this)
    }

    override fun getExpressionNames(): Sequence<String> = expression?.name?.let { listOf(it).asSequence() } ?: emptySequence()

    override fun toString(): String =
        "SubjectPrecondition{" +
                "description='$description'" +
                ", expression=$expression" +
                '}'
}
