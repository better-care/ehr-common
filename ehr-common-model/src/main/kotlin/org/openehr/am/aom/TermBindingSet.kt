package org.openehr.am.aom

import care.better.openehr.am.AmObject
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

class TermBindingSet : AmObject(), Serializable {
    var items: MutableList<TermBindingItem> = mutableListOf()
    lateinit var terminology: String
}