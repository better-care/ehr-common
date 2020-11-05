package org.openehr.base.foundationtypes

import kotlinx.serialization.Serializable

/**
 * @author Primoz Delopst
 */

@Serializable
class IntervalOfDuration : Interval() {
    var lower: String? = null
    var upper: String? = null
}