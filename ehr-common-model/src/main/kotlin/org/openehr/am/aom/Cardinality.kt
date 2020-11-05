package org.openehr.am.aom

import care.better.openehr.am.AmObject
import kotlinx.serialization.Serializable
import org.openehr.base.foundationtypes.IntervalOfInteger

/**
 * @author Primoz Delopst
 */

@Serializable
class Cardinality : AmObject() {
    var isOrdered = false
    var isUnique = false
    lateinit var interval: IntervalOfInteger
}