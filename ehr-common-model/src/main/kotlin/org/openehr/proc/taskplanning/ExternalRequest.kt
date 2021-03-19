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
import org.openehr.rm.common.PartyProxy
import org.openehr.rm.datastructures.ItemStructure
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlType

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */
@XmlType(name = "EXTERNAL_REQUEST", propOrder = [
    "organisation",
    "requestId",
    "otherDetails"])
@Open
class ExternalRequest() : DispatchableAction() {
    companion object {
        private const val serialVersionUID: Long = 0L
    }

    @XmlElement(required = true)
    @Required
    var organisation: PartyProxy? = null

    @XmlElement(name = "request_id", required = true)
    @Required
    var requestId: String? = null

    @XmlElement(name = "other_details")
    var otherDetails: ItemStructure? = null

    constructor(organisation: PartyProxy?, requestId: String?) : this() {
        this.organisation = organisation
        this.requestId = requestId
    }

    override fun accept(visitor: TaskModelVisitor) {
        val visited = visitor.visit(this)
        visitor.afterVisit(this)
        if (visited) {
            acceptPreconditions(visitor)
        }
        visitor.afterAccept(this)
    }

    override fun toString(): String =
        "ExternalRequest{" +
                "organisation=$organisation" +
                ", requestId='$requestId'" +
                ", otherDetails=$otherDetails" +
                "} ${super.toString()}"
}
