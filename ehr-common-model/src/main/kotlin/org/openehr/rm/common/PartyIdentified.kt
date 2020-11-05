package org.openehr.rm.common

import kotlinx.serialization.Serializable
import org.openehr.rm.datatypes.DvIdentifier

/**
 * @author Primoz Delopst
 */

@Serializable
open class PartyIdentified : PartyProxy() {
    var name: String? = null
    var identifiers: MutableList<DvIdentifier> = mutableListOf()
}