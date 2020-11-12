package org.openehr.rm.composition

import care.better.openehr.rm.RmObject
import care.better.platform.annotation.RequiresNotNull
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
    @RequiresNotNull
    var startTime: DvDateTime? = null
    var endTime: DvDateTime? = null
    var location: String? = null

    @RequiresNotNull
    var setting: DvCodedText? = null
    var otherContext: ItemStructure? = null
    var healthCareFacility: PartyIdentified? = null
    var participations: MutableList<Participation> = mutableListOf()
}