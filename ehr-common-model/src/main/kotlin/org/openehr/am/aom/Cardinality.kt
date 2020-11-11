package org.openehr.am.aom

import care.better.openehr.am.AmObject
import org.openehr.base.foundationtypes.IntervalOfInteger
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

class Cardinality : AmObject(), Serializable {
    var isOrdered = false
    var isUnique = false
    lateinit var interval: IntervalOfInteger
}