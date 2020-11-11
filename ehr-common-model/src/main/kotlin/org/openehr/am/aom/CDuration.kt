package org.openehr.am.aom

import org.openehr.base.foundationtypes.IntervalOfDuration

/**
 * @author Primoz Delopst
 */

class CDuration : CPrimitive() {
    var pattern: String? = null
    var range: IntervalOfDuration? = null
    var assumedValue: String? = null
    var defaultValue: String? = null
}