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
import org.openehr.base.basetypes.ObjectRef
import org.openehr.rm.datatypes.DvText
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

@Opened
class ResourceParticipation() : RmObject(), Serializable, VisitableByModelVisitor {

    @Required
    var resourceType: DvText? = null
    var externalRef: ObjectRef? = null

    constructor(resourceType: DvText?) : this() {
        this.resourceType = resourceType
    }

    constructor(resourceType: DvText?, externalRef: ObjectRef?) : this(resourceType) {
        this.externalRef = externalRef
    }

    override fun accept(visitor: TaskModelVisitor) {
        visitor.visit(this)
        visitor.afterVisit(this)
        visitor.afterAccept(this)
    }

    override fun toString(): String =
            "ResourceParticipation{" +
                    "resourceType=$resourceType" +
                    ", externalRef=$externalRef" +
                    '}'
}