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

package org.openehr.am.aom

import org.openehr.base.basetypes.ArchetypeId
import org.openehr.base.basetypes.HierObjectId
import org.openehr.rm.common.AuthoredResource

/**
 * @author Primoz Delopst
 */

class Archetype : AuthoredResource() {
    var uid: HierObjectId? = null
    lateinit var archetypeId: ArchetypeId
    var adlVersion: String? = null
    lateinit var concept: String
    var parentArchetypeId: ArchetypeId? = null
    lateinit var definition: CComplexObject
    var invariants: MutableList<Assertion> = mutableListOf()
    lateinit var ontology: ArchetypeOntology
}