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
import care.better.platform.annotation.Required
import care.better.platform.proc.taskplanning.visitor.TaskModelVisitor
import care.better.platform.proc.taskplanning.visitor.VisitableByModelVisitor
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

@Opened
abstract class SystemCall() : RmObject(), Serializable, VisitableByModelVisitor {

    @Required
    var systemId: String? = null

    @Required
    var callName: String? = null
    var parameterMap: MutableList<ParameterMapping> = mutableListOf()
    var boundParameters: MutableList<ParameterDef<*>> = mutableListOf()

    protected constructor(systemId: String?, callName: String?) : this() {
        this.systemId = systemId
        this.callName = callName
    }

    protected constructor(systemId: String?, callName: String?, parameterMap: MutableList<ParameterMapping>) : this(systemId, callName) {
        this.parameterMap = parameterMap
    }

    fun addParameter(name: String, contextName: String): SystemCall = parameterMap.add(ParameterMapping(name, contextName)).let { this }

    fun addBoundParameter(boundParameter: ParameterDef<*>): SystemCall = boundParameters.add(boundParameter).let { this }

    override fun accept(visitor: TaskModelVisitor) {
        visitor.visit(this)
        visitor.afterVisit(this)
        acceptParameters(visitor)
        visitor.afterAccept(this)
    }

    protected open fun acceptParameters(visitor: TaskModelVisitor) {
        parameterMap.forEach { it.accept(visitor) }
        boundParameters.forEach { it.accept(visitor) }
    }

    override fun toString(): String =
            "SystemCall{" +
                    "systemId=$systemId" +
                    ", callName='$callName" +
                    ", parameterMap=$parameterMap" +
                    ", boundParameters=$boundParameters" +
                    "} ${super.toString()}"
}