package org.openehr.am.aom

import care.better.openehr.am.AmObject
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

class ConstraintBindingSet : AmObject(), Serializable {
    var items: MutableList<ConstraintBindingItem> = mutableListOf()
    lateinit var terminology: String
}