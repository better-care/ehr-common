package org.openehr.am.aom

import org.openehr.base.foundationtypes.IntervalOfDate
import java.math.BigInteger

/**
 * @author Primoz Delopst
 */

class CDate : CPrimitive() {
    var pattern: String? = null
    var timezoneValidity: BigInteger? = null
    var range: IntervalOfDate? = null
    var assumedValue: String? = null
    var defaultValue: String? = null
}