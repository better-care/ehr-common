package org.openehr.am.aom

import kotlinx.serialization.Serializable

/**
 * @author Primoz Delopst
 */

@Serializable
class ArchetypeSlot : CObject() {
    var includes: MutableList<Assertion> = mutableListOf()
    var excludes: MutableList<Assertion> = mutableListOf()
}