package org.openehr.am.aom

import org.openehr.base.foundationtypes.IntervalOfDateTime
import java.math.BigInteger

/**
 * @author Primoz Delopst
 */

class CDateTime : CPrimitive() {
    var pattern: String? = null
    var timezoneValidity: BigInteger? = null
    var range: IntervalOfDateTime? = null
    var assumedValue: String? = null
    var defaultValue: String? = null
}