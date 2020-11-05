package org.openehr.base.foundationtypes

import kotlinx.serialization.Serializable

/**
 * @author Primoz Delopst
 */

@Serializable
class IntervalOfTime : Interval() {
    var lower: String? = null
    var upper: String? = null
}