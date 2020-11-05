package org.openehr.base.foundationtypes

import care.better.openehr.rm.RmObject
import kotlinx.serialization.Serializable

/**
 * @author Primoz Delopst
 */

@Serializable
abstract class Interval : RmObject() {
    var lowerIncluded: Boolean? = null
    var upperIncluded: Boolean? = null
    var lowerUnbounded = false
    var upperUnbounded = false
}