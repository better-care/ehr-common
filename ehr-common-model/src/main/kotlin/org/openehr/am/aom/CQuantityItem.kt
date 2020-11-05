package org.openehr.am.aom

import care.better.openehr.am.AmObject
import kotlinx.serialization.Serializable
import org.openehr.base.foundationtypes.IntervalOfInteger
import org.openehr.base.foundationtypes.IntervalOfReal

/**
 * @author Primoz Delopst
 */

@Serializable
class CQuantityItem : AmObject() {
    var magnitude: IntervalOfReal? = null
    var precision: IntervalOfInteger? = null
    lateinit var units: String
}