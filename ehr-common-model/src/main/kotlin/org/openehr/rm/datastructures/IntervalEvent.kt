package org.openehr.rm.datastructures

import kotlinx.serialization.Serializable
import org.openehr.rm.datatypes.DvCodedText
import org.openehr.rm.datatypes.DvDuration

/**
 * @author Primoz Delopst
 */

@Serializable
class IntervalEvent : Event() {
    lateinit var width: DvDuration
    var sampleCount: Int? = null
    lateinit var mathFunction: DvCodedText
}