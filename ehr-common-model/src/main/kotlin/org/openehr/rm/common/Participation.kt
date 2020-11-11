package org.openehr.rm.common

import care.better.openehr.rm.RmObject
import org.openehr.rm.datatypes.DvCodedText
import org.openehr.rm.datatypes.DvInterval
import org.openehr.rm.datatypes.DvText
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

class Participation : RmObject(), Serializable {
    lateinit var function: DvText
    lateinit var performer: PartyProxy
    var time: DvInterval? = null
    var mode: DvCodedText? = null
}