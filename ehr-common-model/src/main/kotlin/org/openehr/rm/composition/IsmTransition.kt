package org.openehr.rm.composition

import care.better.openehr.rm.RmObject
import care.better.platform.annotation.RequiresNotNull
import org.openehr.rm.datatypes.DvCodedText
import org.openehr.rm.datatypes.DvText
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

class IsmTransition : RmObject(), Serializable {
    @RequiresNotNull
    var currentState: DvCodedText? = null
    var transition: DvCodedText? = null
    var careflowStep: DvCodedText? = null
    var reason: MutableList<DvText> = mutableListOf()
}