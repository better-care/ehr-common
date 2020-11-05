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

package org.openehr.am.aom

import org.openehr.base.basetypes.ArchetypeId
import org.openehr.base.basetypes.HierObjectId
import org.openehr.rm.common.AuthoredResource
import javax.xml.bind.annotation.*

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "ARCHETYPE", propOrder = [
        "uid",
        "archetypeId",
        "adlVersion",
        "concept",
        "parentArchetypeId",
        "definition",
        "invariants",
        "ontology"])
@XmlRootElement

class Archetype : AuthoredResource() {
    companion object {
        private const val serialVersionUID: Long = 0L
    }

    var uid: HierObjectId? = null

    @XmlElement(name = "archetype_id", required = true)
    lateinit var archetypeId: ArchetypeId

    @XmlElement(name = "adl_version")
    var adlVersion: String? = null

    lateinit var concept: String

    @XmlElement(name = "parent_archetype_id")
    var parentArchetypeId: ArchetypeId? = null

    @XmlElement(required = true, type = CComplexObject::class)
    lateinit var definition: CComplexObject

    @XmlElement(type = Assertion::class)
    var invariants: MutableList<Assertion> = mutableListOf()

    @XmlElement(required = true, type = ArchetypeOntology::class)
    lateinit var ontology: ArchetypeOntology
}
