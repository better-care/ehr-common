package org.openehr.am.aom

import org.openehr.base.foundationtypes.IntervalOfTime
import java.math.BigInteger

/**
 * @author Primoz Delopst
 */

class CTime : CPrimitive() {
    var pattern: String? = null
    var timezoneValidity: BigInteger? = null
    var range: IntervalOfTime? = null
    var assumedValue: String? = null
    var defaultValue: String? = null
}