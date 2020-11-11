package org.openehr.am.aom

/**
 * @author Primoz Delopst
 */

class ArchetypeSlot : CObject() {
    var includes: MutableList<Assertion> = mutableListOf()
    var excludes: MutableList<Assertion> = mutableListOf()
}