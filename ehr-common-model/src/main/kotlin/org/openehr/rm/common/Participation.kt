package org.openehr.rm.common

import care.better.openehr.referencemodel.RmObject
import kotlinx.serialization.Serializable
import org.openehr.rm.datatypes.DvCodedText
import org.openehr.rm.datatypes.DvInterval
import org.openehr.rm.datatypes.DvText

/**
 * @author Primoz Delopst
 */

@Serializable
class Participation : RmObject() {
    lateinit var function: DvText
    lateinit var performer: PartyProxy
    var time: DvInterval? = null
    var mode: DvCodedText? = null
}