package org.openehr.am.aom

import org.openehr.base.foundationtypes.IntervalOfInteger

/**
 * @author Primoz Delopst
 */

class CInteger : CPrimitive() {
    var list: MutableList<Int> = mutableListOf()
    var range: IntervalOfInteger? = null
    var assumedValue: Int? = null
    var defaultValue: Int? = null
}