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

package org.openehr.rm.ehr

import care.better.openehr.rm.RmObject
import org.openehr.base.basetypes.HierObjectId
import org.openehr.rm.datatypes.DvDateTime
import java.io.Serializable
import java.util.*
import javax.xml.bind.annotation.*

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PATIENT_EHR", namespace = "http://schemas.openehr.org/v1", propOrder = ["systemId", "ehrId", "timeCreated", "ehrStatus"])
@XmlRootElement(namespace = "http://schemas.openehr.org/v1")
class Ehr : RmObject(), Serializable {
    companion object {
        private const val serialVersionUID: Long = 0L
    }

    @XmlElement(name = "system_id")
    var systemId: HierObjectId? = null

    @XmlElement(name = "ehr_id")
    var ehrId: HierObjectId? = null

    @XmlElement(name = "time_created")
    var timeCreated: DvDateTime? = null

    @XmlElement(name = "ehr_status")
    var ehrStatus: EhrStatus? = null


    override fun equals(other: Any?): Boolean =
        when {
            this === other -> true
            javaClass != other?.javaClass -> false
            else -> (other as Ehr).ehrId == ehrId
        }

    override fun hashCode(): Int = Objects.hash(ehrId)
}
