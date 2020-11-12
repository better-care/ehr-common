package org.openehr.rm.datastructures

import care.better.platform.annotation.RequiresNotNull
import org.openehr.rm.datatypes.DvCodedText
import org.openehr.rm.datatypes.DvDuration

/**
 * @author Primoz Delopst
 */

class IntervalEvent : Event() {
    @RequiresNotNull
    var width: DvDuration? = null
    var sampleCount: Int? = null

    @RequiresNotNull
    var mathFunction: DvCodedText? = null
}