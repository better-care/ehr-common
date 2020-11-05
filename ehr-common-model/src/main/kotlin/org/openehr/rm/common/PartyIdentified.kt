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
import org.openehr.base.basetypes.PartyRef
import org.openehr.rm.datatypes.DvIdentifier
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlSeeAlso
import javax.xml.bind.annotation.XmlType

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "PARTY_IDENTIFIED", propOrder = [
        "name",
        "identifiers"])
@XmlSeeAlso(PartyRelated::class)
@Open
class PartyIdentified() : PartyProxy() {

    companion object {
        private const val serialVersionUID: Long = 0L


        /**
         * Creates a [PartyIdentified] from a party name
         *
         * @param name party name
         * @return [PartyIdentified]
         */
        @JvmStatic
        fun forName(name: String): PartyIdentified = PartyIdentified(name)
    }

    @JvmOverloads
    constructor(
            name: String,
            identifiers: MutableList<DvIdentifier> = mutableListOf(),
            externalRef: PartyRef? = null) : this() {
        this.name = name
        this.identifiers = identifiers
        super.externalRef = externalRef
    }

    var name: String? = null

    var identifiers: MutableList<DvIdentifier> = mutableListOf()
}
