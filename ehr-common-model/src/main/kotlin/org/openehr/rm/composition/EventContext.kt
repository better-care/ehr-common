package org.openehr.rm.composition

import care.better.openehr.rm.RmObject
import kotlinx.serialization.Serializable
import org.openehr.rm.common.Participation
import org.openehr.rm.common.PartyIdentified
import org.openehr.rm.datastructures.ItemStructure
import org.openehr.rm.datatypes.DvCodedText
import org.openehr.rm.datatypes.DvDateTime

/**
 * @author Primoz Delopst
 */

@Serializable
class EventContext : RmObject() {
    lateinit var startTime: DvDateTime
    var endTime: DvDateTime? = null
    var location: String? = null
    lateinit var setting: DvCodedText
    var otherContext: ItemStructure? = null
    var healthCareFacility: PartyIdentified? = null
    var participations: MutableList<Participation> = mutableListOf()
}