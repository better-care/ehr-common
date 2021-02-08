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
import org.openehr.rm.datatypes.DvCodedText
import org.openehr.rm.datatypes.DvDateTime
import org.openehr.rm.datatypes.DvText
import java.io.Serializable
import javax.xml.bind.annotation.*

/**
 * @author Primoz Delopst
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AUDIT_DETAILS", propOrder = [
    "systemId",
    "committer",
    "timeCommitted",
    "changeType",
    "description"])
@XmlSeeAlso(Attestation::class)
@Open
class AuditDetails : RmObject(), Serializable {
    companion object {
        @JvmStatic
        private val serialVersionUID: Long = 0L
    }

    @XmlElement(name = "system_id", required = true)
    @Required
    var systemId: String? = null

    @XmlElement(required = true)
    @Required
    var committer: PartyProxy? = null

    @XmlElement(name = "time_committed", required = true)
    @Required
    var timeCommitted: DvDateTime? = null

    @XmlElement(name = "change_type", required = true)
    @Required
    var changeType: DvCodedText? = null

    var description: DvText? = null
}
