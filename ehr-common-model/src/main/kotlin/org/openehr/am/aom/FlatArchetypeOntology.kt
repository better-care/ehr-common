package org.openehr.am.aom

import care.better.platform.annotation.RequiresNotNull

/**
 * @author Primoz Delopst
 */

class FlatArchetypeOntology : ArchetypeOntology() {
    @RequiresNotNull
    var archetypeId: String? = null
}