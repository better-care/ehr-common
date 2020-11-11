package org.openehr.base.foundationtypes

import care.better.openehr.rm.RmObject
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

abstract class Interval : RmObject(), Serializable {
    var lowerIncluded: Boolean? = null
    var upperIncluded: Boolean? = null
    var lowerUnbounded = false
    var upperUnbounded = false
}