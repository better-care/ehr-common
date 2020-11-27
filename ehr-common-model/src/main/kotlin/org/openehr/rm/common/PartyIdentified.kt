/* Copyright 2020-2025 Better Ltd (www.better.care)
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

import care.better.platform.annotation.Opened
import org.openehr.rm.datatypes.DvIdentifier

/**
 * @author Primoz Delopst
 */

@Opened
open class PartyIdentified : PartyProxy() {

    companion object {
        /**
         * Creates a [PartyIdentified] from a party name
         *
         * @param name party name
         * @return [PartyIdentified]
         */
        @JvmStatic
        fun create(name: String): PartyIdentified = PartyIdentified().apply { this.name = name }
    }

    var name: String? = null
    var identifiers: MutableList<DvIdentifier> = mutableListOf()
}