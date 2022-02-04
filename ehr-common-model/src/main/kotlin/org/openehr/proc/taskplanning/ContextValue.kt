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
import care.better.platform.proc.taskplanning.visitor.VisitableByModelVisitor
import javax.xml.bind.annotation.*

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */

@SuppressWarnings("ClassReferencesSubclass")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CONTEXT_VALUE", propOrder = ["name", "type"])
@XmlSeeAlso(value = [ContextVariable::class, ContextExpression::class, ContextConstant::class])
@Open
abstract class ContextValue<T>() : RmObject(), VisitableByModelVisitor {
    companion object {
        private const val serialVersionUID: Long = 0L
    }

    @XmlElement(required = true)
    @Required
    var name: String? = null

    @XmlElement(required = true)
    @Required
    private var type: ExprTypeDef<T>? = null

    protected constructor(type: ExprTypeDef<T>?) : this() {
        this.type = type
    }

    protected constructor(name: String?, type: ExprTypeDef<T>?) : this(type) {
        this.name = name
    }

    fun getType(): ExprTypeDef<T>? = type

    fun setType(type: ExprTypeDef<T>?) {
        this.type = type
    }

    override fun toString(): String =
        "ContextValue{" +
                "name='$name'" +
                ", type=$type" +
                "} ${super.toString()}"
}
