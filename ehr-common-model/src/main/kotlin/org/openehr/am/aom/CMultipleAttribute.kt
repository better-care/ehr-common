package org.openehr.am.aom

import care.better.platform.annotation.RequiresNotNull

/**
 * @author Primoz Delopst
 */

class CMultipleAttribute : CAttribute() {
    @RequiresNotNull
    var cardinality: Cardinality? = null
    var groups: MutableList<CObjectGroup> = mutableListOf()
}