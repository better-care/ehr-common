package org.openehr.am.aom

import care.better.openehr.am.AmObject
import care.better.platform.annotation.RequiresNotNull
import org.openehr.base.foundationtypes.IntervalOfInteger
import org.openehr.base.foundationtypes.IntervalOfReal
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

class CQuantityItem : AmObject(), Serializable {
    var magnitude: IntervalOfReal? = null
    var precision: IntervalOfInteger? = null
    @RequiresNotNull
    var units: String? = null
}