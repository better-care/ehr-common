package org.openehr.rm.composition

import kotlinx.serialization.Serializable
import org.openehr.rm.datatypes.DvDateTime
import org.openehr.rm.datatypes.DvParsable
import org.openehr.rm.datatypes.DvText

/**
 * @author Primoz Delopst
 */

@Serializable
class Instruction : CareEntry() {
    lateinit var narrative: DvText
    var expiryTime: DvDateTime? = null
    var wfDefinition: DvParsable? = null
    var activities: MutableList<Activity> = mutableListOf()
}