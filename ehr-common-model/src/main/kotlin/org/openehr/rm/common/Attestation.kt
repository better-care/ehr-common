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

package org.openehr.rm.common

import care.better.platform.annotation.Open
import care.better.platform.annotation.Required
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.openehr.rm.datatypes.*
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
    name = "ATTESTATION", propOrder = [
        "attestedView",
        "proof",
        "items",
        "reason",
        "isPending"])
@Serializable
@SerialName("ATTESTATION")
@Open
class Attestation() : AuditDetails() {
    @JvmOverloads
    constructor(
            reason: DvText,
            attestedView: DvMultimedia? = null,
            proof: String? = null,
            items: MutableList<DvEhrUri> = mutableListOf(),
            isPending: Boolean = false,
            systemId: String? = null,
            committer: PartyProxy? = null,
            timeCommitted: DvDateTime? = null,
            changeType: DvCodedText? = null,
            description: DvText? = null) : this() {
        this.reason = reason
        this.attestedView = attestedView
        this.proof = proof
        this.items = items
        this.isPending = isPending
        this.systemId = systemId
        this.committer = committer
        this.timeCommitted = timeCommitted
        this.changeType = changeType
        this.description = description
    }

    companion object {
        private const val serialVersionUID: Long = 0L
    }

    @XmlElement(name = "attested_view")
    @SerialName("attested_view")
    var attestedView: DvMultimedia? = null
    var proof: String? = null
    var items: MutableList<DvEhrUri> = mutableListOf()

    @XmlElement(required = true)
    @Required
    var reason: DvText? = null

    @XmlElement(name = "is_pending", defaultValue = "false")
    @SerialName("is_pending")
    var isPending: Boolean = false

    override fun visit(attributeName: String, ctx: care.better.platform.visitor.RmVisitorContext) {
        // Visit parent properties
        systemId?.let { ctx.visitValue("system_id", it) }
        committer?.visit("committer", ctx)
        timeCommitted?.visit("time_committed", ctx)
        changeType?.visit("change_type", ctx)
        description?.visit("description", ctx)

        // Visit own properties
        attestedView?.visit("attested_view", ctx)
        proof?.let { ctx.visitValue("proof", it) }
        items.forEach { it.visit("items", ctx) }
        reason?.visit("reason", ctx)
        ctx.visitValue("is_pending", isPending)

        ctx.visitObject(attributeName, this, "ATTESTATION")
    }
}
