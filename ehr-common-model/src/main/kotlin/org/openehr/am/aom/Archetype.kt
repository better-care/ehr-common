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