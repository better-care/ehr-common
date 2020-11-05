package org.openehr.am.aom

import kotlinx.serialization.Serializable
import org.openehr.base.foundationtypes.IntervalOfDuration

/**
 * @author Primoz Delopst
 */

@Serializable
class CDuration : CPrimitive() {
    var pattern: String? = null
    var range: IntervalOfDuration? = null
    var assumedValue: String? = null
    var defaultValue: String? = null
}