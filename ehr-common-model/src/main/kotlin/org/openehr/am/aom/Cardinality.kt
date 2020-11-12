package org.openehr.am.aom

import care.better.openehr.am.AmObject
import care.better.platform.annotation.RequiresNotNull
import org.openehr.base.foundationtypes.IntervalOfInteger
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

class Cardinality : AmObject(), Serializable {
    var isOrdered = false
    var isUnique = false

    @RequiresNotNull
    var interval: IntervalOfInteger? = null
}