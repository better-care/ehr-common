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
import javax.xml.bind.annotation.*

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */
@SuppressWarnings("ClassReferencesSubclass")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CONTEXT_EXPRESSION", propOrder = ["expression"])
@XmlSeeAlso(BooleanContextExpression::class)
@Open
class ContextExpression<T> : ContextValue<T> {
    companion object {
        private const val serialVersionUID: Long = 0L
    }

    @XmlElement(required = true)
    @Required
    var expression: String? = null

    constructor()

    constructor(type: ExprTypeDef<T>?) : super(type)

    constructor(type: ExprTypeDef<T>?, expression: String?) : this(type) {
        this.expression = expression
    }

    override fun accept(visitor: TaskModelVisitor) {
        visitor.visit(this)
        visitor.afterVisit(this)
        visitor.afterAccept(this)
    }

    override fun toString(): String =
        "ContextExpression{" +
                "expression='$expression'" +
                "} ${super.toString()}"
}
