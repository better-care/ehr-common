package org.openehr.rm.common

import care.better.openehr.rm.RmObject
import care.better.platform.annotation.RequiresNotNull
import org.openehr.rm.datatypes.DvCodedText
import org.openehr.rm.datatypes.DvInterval
import org.openehr.rm.datatypes.DvText
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

class Participation : RmObject(), Serializable {
    @RequiresNotNull
    var function: DvText? = null

    @RequiresNotNull
    var performer: PartyProxy? = null
    var time: DvInterval? = null
    var mode: DvCodedText? = null
}