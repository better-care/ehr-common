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
import org.openehr.base.basetypes.ObjectVersionId
import org.openehr.rm.datatypes.DvCodedText
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
    name = "ORIGINAL_VERSION", propOrder = [
        "uid",
        "data",
        "precedingVersionUid",
        "otherInputVersionUids",
        "attestations",
        "lifecycleState"])
@Open
class OriginalVersion : Version() {
    companion object {
        private const val serialVersionUID: Long = 0L
    }

    @XmlElement(required = true)
    @Required
    var uid: ObjectVersionId? = null

    var data: Any? = null

    @XmlElement(name = "preceding_version_uid")
    var precedingVersionUid: ObjectVersionId? = null

    @XmlElement(name = "other_input_version_uids")
    var otherInputVersionUids: MutableList<ObjectVersionId> = mutableListOf()

    var attestations: MutableList<Attestation> = mutableListOf()

    @XmlElement(name = "lifecycle_state", required = true)
    @Required
    var lifecycleState: DvCodedText? = null
}
