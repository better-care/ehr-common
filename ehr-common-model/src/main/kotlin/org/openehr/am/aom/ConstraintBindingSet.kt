package org.openehr.am.aom

import care.better.openehr.am.AmObject
import care.better.platform.annotation.RequiresNotNull
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

class ConstraintBindingSet : AmObject(), Serializable {
    var items: MutableList<ConstraintBindingItem> = mutableListOf()

    @RequiresNotNull
    var terminology: String? = null
}