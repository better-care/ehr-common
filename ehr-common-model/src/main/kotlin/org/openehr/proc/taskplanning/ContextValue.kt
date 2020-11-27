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
import care.better.platform.annotation.Opened
import care.better.platform.proc.taskplanning.visitor.VisitableByModelVisitor

/**
 * @author Primoz Delopst
 */

@Opened
abstract class ContextValue<T>() : RmObject(), VisitableByModelVisitor {
    var name: String? = null
    private var type: ExprTypeDef<T>? = null

    protected constructor(type: ExprTypeDef<T>?) : this() {
        this.type = type
    }

    protected constructor(name: String?, type: ExprTypeDef<T>?) : this(type) {
        this.name = name
    }

    open fun getType(): ExprTypeDef<T>? = type

    open fun setType(type: ExprTypeDef<T>?) {
        this.type = type
    }

    override fun toString(): String =
            "ContextValue{" +
                    "name='$name'" +
                    ", type=$type" +
                    "} ${super.toString()}"
}