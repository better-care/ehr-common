package org.openehr.base.foundationtypes

import kotlinx.serialization.Serializable

/**
 * @author Primoz Delopst
 */

@Serializable
class IntervalOfInteger : Interval() {
    var lower: Int? = null
    var upper: Int? = null
}