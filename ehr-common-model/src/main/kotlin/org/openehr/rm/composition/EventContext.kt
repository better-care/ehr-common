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

import care.better.openehr.rm.RmObject
import care.better.platform.annotation.Open
import care.better.platform.annotation.Required
import org.openehr.rm.common.Participation
import org.openehr.rm.common.PartyIdentified
import org.openehr.rm.datastructures.ItemStructure
import org.openehr.rm.datatypes.DvCodedText
import org.openehr.rm.datatypes.DvDateTime
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

@Open
class EventContext : RmObject(), Serializable {
    companion object {
        @JvmStatic
        private val serialVersionUID: Long = 0L
    }

    @Required
    var startTime: DvDateTime? = null
    var endTime: DvDateTime? = null
    var location: String? = null

    @Required
    var setting: DvCodedText? = null
    var otherContext: ItemStructure? = null
    var healthCareFacility: PartyIdentified? = null
    var participations: MutableList<Participation> = mutableListOf()
}