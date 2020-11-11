package org.openehr.rm.composition

import org.openehr.rm.datatypes.DvDateTime
import org.openehr.rm.datatypes.DvParsable
import org.openehr.rm.datatypes.DvText

/**
 * @author Primoz Delopst
 */

class Instruction : CareEntry() {
    lateinit var narrative: DvText
    var expiryTime: DvDateTime? = null
    var wfDefinition: DvParsable? = null
    var activities: MutableList<Activity> = mutableListOf()
}