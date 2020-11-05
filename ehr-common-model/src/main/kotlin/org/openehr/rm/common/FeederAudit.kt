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
import org.openehr.rm.datatypes.DvEncapsulated
import org.openehr.rm.datatypes.DvIdentifier
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
    name = "FEEDER_AUDIT", propOrder = [
        "originatingSystemItemIds",
        "feederSystemItemIds",
        "originalContent",
        "originatingSystemAudit",
        "feederSystemAudit"])
@Open
class FeederAudit() : RmObject(), Serializable {
    companion object {
        private const val serialVersionUID: Long = 0L
    }

    @JvmOverloads
    constructor(
            originatingSystemItemIds: MutableList<DvIdentifier> = mutableListOf(),
            feederSystemItemIds: MutableList<DvIdentifier> = mutableListOf(),
            originalContent: DvEncapsulated? = null,
            originatingSystemAudit: FeederAuditDetails,
            feederSystemAudit: FeederAuditDetails? = null) : this() {
        this.originatingSystemAudit = originatingSystemAudit
        this.feederSystemItemIds = feederSystemItemIds
        this.originalContent = originalContent
        this.originatingSystemAudit = originatingSystemAudit
        this.feederSystemAudit = feederSystemAudit
    }

    @XmlElement(name = "originating_system_item_ids")
    var originatingSystemItemIds: MutableList<DvIdentifier> = mutableListOf()

    @XmlElement(name = "feeder_system_item_ids")
    var feederSystemItemIds: MutableList<DvIdentifier> = mutableListOf()

    @XmlElement(name = "original_content")
    var originalContent: DvEncapsulated? = null

    @XmlElement(name = "originating_system_audit", required = true)
    @Required
    var originatingSystemAudit: FeederAuditDetails? = null

    @XmlElement(name = "feeder_system_audit")
    var feederSystemAudit: FeederAuditDetails? = null

}
