package org.openehr.am.aom

import kotlinx.serialization.Serializable

/**
 * @author Primoz Delopst
 */

@Serializable
class FlatArchetypeOntology : ArchetypeOntology() {
    lateinit var archetypeId: String
}