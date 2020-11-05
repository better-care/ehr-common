package org.openehr.base.foundationtypes

import kotlinx.serialization.Serializable

/**
 * @author Primoz Delopst
 */

@Serializable
class IntervalOfReal : Interval(){
    var lower: Float? = null
    var upper: Float? = null
}