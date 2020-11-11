package org.openehr.rm.composition

import care.better.openehr.rm.RmObject
import org.openehr.rm.datatypes.DvCodedText
import org.openehr.rm.datatypes.DvText
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

class IsmTransition : RmObject(), Serializable {
    lateinit var currentState: DvCodedText
    var transition: DvCodedText? = null
    var careflowStep: DvCodedText? = null
    var reason: MutableList<DvText> = mutableListOf()
}