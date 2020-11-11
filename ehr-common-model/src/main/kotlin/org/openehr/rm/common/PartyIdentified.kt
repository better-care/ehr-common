package org.openehr.rm.common

import org.openehr.rm.datatypes.DvIdentifier

/**
 * @author Primoz Delopst
 */

open class PartyIdentified : PartyProxy() {
    var name: String? = null
    var identifiers: MutableList<DvIdentifier> = mutableListOf()
}