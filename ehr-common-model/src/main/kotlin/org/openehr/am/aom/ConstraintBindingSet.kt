package org.openehr.am.aom

import care.better.openehr.am.AmObject
import kotlinx.serialization.Serializable

/**
 * @author Primoz Delopst
 */

@Serializable
class ConstraintBindingSet : AmObject() {
    var items: MutableList<ConstraintBindingItem> = mutableListOf()
    lateinit var terminology: String
}