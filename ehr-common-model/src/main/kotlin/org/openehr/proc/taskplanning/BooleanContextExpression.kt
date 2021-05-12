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
import care.better.platform.proc.taskplanning.visitor.TaskModelVisitor
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlType

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BOOLEAN_CONTEXT_EXPRESSION")
@Open
class BooleanContextExpression : ContextExpression<Boolean> {
    companion object {
        private const val serialVersionUID: Long = 0L
    }

    constructor() : super(TypeDefBoolean.INSTANCE)

    constructor(expression: String?) : super(TypeDefBoolean.INSTANCE, expression)

    override fun getType(): TypeDefBoolean? = super.getType() as TypeDefBoolean?

    override fun setType(type: ExprTypeDef<Boolean>?) {
        if (TypeDefBoolean.INSTANCE != type) {
            throw UnsupportedOperationException("TypeDefBoolean required here.")
        }
        super.setType(type)
    }

    fun setType(type: TypeDefBoolean) {
        super.setType(type)
    }

    override fun accept(visitor: TaskModelVisitor) {
        visitor.visit(this)
        visitor.afterVisit(this)
        visitor.afterAccept(this)
    }

    override fun toString(): String = "BooleanContextExpression{} ${super.toString()}"
}
