package org.openehr.rm.common

import org.openehr.rm.datatypes.DvCodedText

/**
 * @author Primoz Delopst
 */

class PartyRelated : PartyIdentified() {
    lateinit var relationship: DvCodedText
}