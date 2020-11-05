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

import care.better.platform.annotation.OpenEhrName
import care.better.platform.annotation.Required
import org.openehr.rm.common.Locatable
import org.openehr.rm.common.PartySelf
import org.openehr.rm.datastructures.ItemStructure
import javax.xml.bind.annotation.*

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EHR_STATUS", namespace = "http://schemas.openehr.org/v1", propOrder = ["subject", "queryable", "modifiable", "otherDetails"])
@XmlRootElement(namespace = "http://schemas.openehr.org/v1")
class EhrStatus : Locatable() {
    companion object {
        private const val serialVersionUID: Long = 0L
    }

    @XmlElement(required = true)
    @Required
    var subject: PartySelf? = null

    @XmlElement(name = "is_queryable")
    @OpenEhrName("is_queryable")
    var queryable: Boolean = true

    @XmlElement(name = "is_modifiable")
    @OpenEhrName("is_modifiable")
    var modifiable: Boolean = true

    @XmlElement(name = "other_details")
    var otherDetails: ItemStructure? = null
}
