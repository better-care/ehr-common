package org.openehr.am.aom

import kotlinx.serialization.Serializable

/**
 * @author Primoz Delopst
 */

@Serializable
class CMultipleAttribute : CAttribute() {
    lateinit var cardinality: Cardinality
    var groups: MutableList<CObjectGroup> = mutableListOf()
}