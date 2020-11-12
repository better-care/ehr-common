package org.openehr.am.aom

import care.better.platform.annotation.RequiresNotNull
import org.openehr.base.basetypes.ArchetypeId
import org.openehr.base.basetypes.HierObjectId
import org.openehr.rm.common.AuthoredResource

/**
 * @author Primoz Delopst
 */

class Archetype : AuthoredResource() {
    var uid: HierObjectId? = null

    @RequiresNotNull
    var archetypeId: ArchetypeId? = null
    var adlVersion: String? = null

    @RequiresNotNull
    var concept: String? = null
    var parentArchetypeId: ArchetypeId? = null

    @RequiresNotNull
    var definition: CComplexObject? = null
    var invariants: MutableList<Assertion> = mutableListOf()

    @RequiresNotNull
    var ontology: ArchetypeOntology? = null
}