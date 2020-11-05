package org.openehr.am.aom

import kotlinx.serialization.Serializable
import org.openehr.base.foundationtypes.IntervalOfInteger

/**
 * @author Primoz Delopst
 */

@Serializable
class CInteger : CPrimitive() {
    var list: MutableList<Int> = mutableListOf()
    var range: IntervalOfInteger? = null
    var assumedValue: Int? = null
    var defaultValue: Int? = null
}