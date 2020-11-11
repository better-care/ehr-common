package org.openehr.rm.composition

import care.better.openehr.rm.RmObject
import org.openehr.rm.common.Participation
import org.openehr.rm.common.PartyIdentified
import org.openehr.rm.datastructures.ItemStructure
import org.openehr.rm.datatypes.DvCodedText
import org.openehr.rm.datatypes.DvDateTime
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

class EventContext : RmObject(), Serializable {
    lateinit var startTime: DvDateTime
    var endTime: DvDateTime? = null
    var location: String? = null
    lateinit var setting: DvCodedText
    var otherContext: ItemStructure? = null
    var healthCareFacility: PartyIdentified? = null
    var participations: MutableList<Participation> = mutableListOf()
}