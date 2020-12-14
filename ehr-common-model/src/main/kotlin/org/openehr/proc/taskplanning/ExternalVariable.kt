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

/**
 * @author Primoz Delopst
 */

@Open
abstract class ExternalVariable<T> : ContextVariable<T> {
    var populatingRequest: SystemCall? = null

    constructor()

    protected constructor(type: ExprTypeDef<T>?, name: String?) : super(name, type)

    protected constructor(type: ExprTypeDef<T>?, name: String?, populatingRequest: SystemCall?) : this(type, name) {
        this.populatingRequest = populatingRequest
    }

    override fun accept(visitor: TaskModelVisitor) {
        visitor.visit(this)
        visitor.afterVisit(this)
        populatingRequest?.also { it.accept(visitor) }
        visitor.afterAccept(this)
    }

    override fun toString(): String =
            "ExternalVariable{" +
                    "populatingRequest=$populatingRequest" +
                    "} ${super.toString()}"
}