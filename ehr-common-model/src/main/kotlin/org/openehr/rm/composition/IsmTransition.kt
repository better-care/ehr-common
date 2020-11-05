package org.openehr.rm.composition

import care.better.openehr.referencemodel.RmObject
import kotlinx.serialization.Serializable
import org.openehr.rm.datatypes.DvCodedText
import org.openehr.rm.datatypes.DvText

/**
 * @author Primoz Delopst
 */

@Serializable
class IsmTransition : RmObject() {
    lateinit var currentState: DvCodedText
    var transition: DvCodedText? = null
    var careflowStep: DvCodedText? = null
    var reason: MutableList<DvText> = mutableListOf()
}