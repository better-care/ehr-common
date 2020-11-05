package org.openehr.am.aom

import kotlinx.serialization.Serializable
import org.openehr.base.foundationtypes.IntervalOfReal

/**
 * @author Primoz Delopst
 */

@Serializable
class CReal : CPrimitive() {
    var list: MutableList<Float> = mutableListOf()
    var range: IntervalOfReal? = null
    var assumedValue: Float? = null
    var defaultValue: Float? = null
}