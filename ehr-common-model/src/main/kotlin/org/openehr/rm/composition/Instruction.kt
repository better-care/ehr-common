package org.openehr.rm.composition

import care.better.platform.annotation.RequiresNotNull
import org.openehr.rm.datatypes.DvDateTime
import org.openehr.rm.datatypes.DvParsable
import org.openehr.rm.datatypes.DvText

/**
 * @author Primoz Delopst
 */

class Instruction : CareEntry() {
    @RequiresNotNull
    var narrative: DvText? = null
    var expiryTime: DvDateTime? = null
    var wfDefinition: DvParsable? = null
    var activities: MutableList<Activity> = mutableListOf()
}