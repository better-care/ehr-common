package org.openehr.rm.common

import kotlinx.serialization.Serializable
import org.openehr.rm.datatypes.DvCodedText

/**
 * @author Primoz Delopst
 */

@Serializable
class PartyRelated : PartyIdentified() {
    lateinit var relationship: DvCodedText
}