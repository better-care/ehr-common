package org.openehr.rm.datastructures

import kotlinx.serialization.Serializable
import org.openehr.rm.common.Locatable
import org.openehr.rm.datatypes.DvDateTime
import org.openehr.rm.datatypes.DvDuration

/**
 * @author Primoz Delopst
 */

@Serializable
class History : Locatable() {
    lateinit var origin: DvDateTime
    var period: DvDuration? = null
    var duration: DvDuration? = null
    var events: MutableList<Event> = mutableListOf()
    var summary: ItemStructure? = null
}