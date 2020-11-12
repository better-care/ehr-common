package org.openehr.am.aom

import care.better.openehr.am.AmObject
import care.better.platform.annotation.RequiresNotNull
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

class TermBindingSet : AmObject(), Serializable {
    var items: MutableList<TermBindingItem> = mutableListOf()

    @RequiresNotNull
    var terminology: String? = null
}