package org.openehr.am.aom

import care.better.openehr.archetypemodel.AmObject
import kotlinx.serialization.Serializable

/**
 * @author Primoz Delopst
 */

@Serializable
class TermBindingSet : AmObject() {
    var items: MutableList<TermBindingItem> = mutableListOf()
    lateinit var terminology: String
}