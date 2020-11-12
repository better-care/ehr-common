package org.openehr.rm.common

import care.better.platform.annotation.RequiresNotNull
import org.openehr.rm.datatypes.DvCodedText

/**
 * @author Primoz Delopst
 */

class PartyRelated : PartyIdentified() {
    @RequiresNotNull
    var relationship: DvCodedText? = null
}