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

import care.better.openehr.rm.RmObject
import care.better.platform.annotation.Open
import care.better.platform.annotation.Required
import care.better.platform.visitor.RmVisitorContext
import kotlinx.serialization.SerialName
import org.openehr.rm.datastructures.ItemStructure
import org.openehr.rm.datatypes.DvDateTime
import java.io.Serializable
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
    name = "FEEDER_AUDIT_DETAILS", propOrder = [
        "systemId",
        "location",
        "provider",
        "subject",
        "time",
        "versionId",
        "otherDetails"])
@kotlinx.serialization.Serializable
@SerialName("FEEDER_AUDIT_DETAILS")
@Open
class FeederAuditDetails() : RmObject(), Serializable {
    companion object {
        private const val serialVersionUID: Long = 0L
    }

    @JvmOverloads
    constructor(
            systemId: String,
            location: PartyIdentified? = null,
            provider: PartyIdentified? = null,
            subject: PartyProxy? = null,
            time: DvDateTime? = null,
            versionId: String? = null,
            otherDetails: ItemStructure? = null) : this() {
        this.systemId = systemId
        this.location = location
        this.provider = provider
        this.subject = subject
        this.time = time
        this.versionId = versionId
        this.otherDetails = otherDetails
    }

    @XmlElement(name = "system_id", required = true)
    @Required
    @SerialName("system_id")
    var systemId: String? = null

    var location: PartyIdentified? = null

    var provider: PartyIdentified? = null

    var subject: PartyProxy? = null

    var time: DvDateTime? = null

    @XmlElement(name = "version_id")
    @SerialName("version_id")
    var versionId: String? = null

    @XmlElement(name = "other_details")
    @SerialName("other_details")
    var otherDetails: ItemStructure? = null

    fun visit(attributeName: String, ctx: RmVisitorContext) {
        if (!ctx.visitObject(attributeName, this, "FEEDER_AUDIT_DETAILS")) return
        systemId?.let { ctx.visitValue("system_id", it) }
        location?.visit("location", ctx)
        provider?.visit("provider", ctx)
        subject?.visit("subject", ctx)
        time?.visit("time", ctx)
        versionId?.let { ctx.visitValue("version_id", it) }
        otherDetails?.visit("other_details", ctx)
    }
}
