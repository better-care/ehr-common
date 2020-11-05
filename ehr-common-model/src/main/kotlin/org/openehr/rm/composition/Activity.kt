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

package org.openehr.rm.composition

import care.better.platform.annotation.Open
import care.better.platform.annotation.Required
import org.openehr.rm.common.Locatable
import org.openehr.rm.datastructures.ItemStructure
import org.openehr.rm.datatypes.DvParsable
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
    name = "ACTIVITY", propOrder = [
        "description",
        "timing",
        "actionArchetypeId"])
@Open
class Activity : Locatable() {
    companion object {
        private const val serialVersionUID: Long = 0L
    }

    @XmlElement(required = true)
    @Required
    var description: ItemStructure? = null

    var timing: DvParsable? = null

    @XmlElement(name = "action_archetype_id", required = true)
    @Required
    var actionArchetypeId: String? = null
}
