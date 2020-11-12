package org.openehr.rm.datastructures

import care.better.platform.annotation.RequiresNotNull
import org.openehr.rm.common.Locatable
import org.openehr.rm.datatypes.DvDateTime
import org.openehr.rm.datatypes.DvDuration

/**
 * @author Primoz Delopst
 */

class History : Locatable() {
    @RequiresNotNull
    var origin: DvDateTime? = null
    var period: DvDuration? = null
    var duration: DvDuration? = null
    var events: MutableList<Event> = mutableListOf()
    var summary: ItemStructure? = null
}