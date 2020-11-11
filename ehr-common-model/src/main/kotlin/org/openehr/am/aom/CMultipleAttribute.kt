package org.openehr.am.aom

/**
 * @author Primoz Delopst
 */

class CMultipleAttribute : CAttribute() {
    lateinit var cardinality: Cardinality
    var groups: MutableList<CObjectGroup> = mutableListOf()
}