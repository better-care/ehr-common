package org.openehr.am.aom

import org.openehr.base.foundationtypes.IntervalOfReal
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

class CReal : CPrimitive(), Serializable {
    var list: MutableList<Float> = mutableListOf()
    var range: IntervalOfReal? = null
    var assumedValue: Float? = null
    var defaultValue: Float? = null
}