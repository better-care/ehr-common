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

import care.better.platform.annotation.RequiresNotNull
import care.better.platform.proc.taskplanning.visitor.TaskModelVisitor
import org.openehr.rm.common.PartyProxy
import org.openehr.rm.datastructures.ItemStructure

/**
 * @author Primoz Delopst
 */

class ExternalRequest() : DispatchableAction() {

    @RequiresNotNull
    var organisation: PartyProxy? = null

    @RequiresNotNull
    var requestId: String? = null
    var otherDetails: ItemStructure? = null

    constructor(organisation: PartyProxy?, requestId: String?) : this() {
        this.organisation = organisation
        this.requestId = requestId
    }

    override fun addSubjectPrecondition(subjectPrecondition: SubjectPrecondition): ExternalRequest =
            super.addSubjectPrecondition(subjectPrecondition) as ExternalRequest

    override fun accept(visitor: TaskModelVisitor) {
        visitor.visit(this)
        visitor.afterVisit(this)
        acceptPreconditions(visitor)
        visitor.afterAccept(this)
    }

    override fun toString(): String =
            "ExternalRequest{" +
                    "organisation=$organisation" +
                    ", requestId='$requestId'" +
                    ", otherDetails=$otherDetails" +
                    "} ${super.toString()}"
}