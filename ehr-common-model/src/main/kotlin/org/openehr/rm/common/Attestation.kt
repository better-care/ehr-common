package org.openehr.rm.common

import care.better.platform.annotation.RequiresNotNull
import org.openehr.rm.datatypes.DvEhrUri
import org.openehr.rm.datatypes.DvMultimedia
import org.openehr.rm.datatypes.DvText

/**
 * @author Primoz Delopst
 */

class Attestation : AuditDetails() {
    var attestedView: DvMultimedia? = null
    var proof: String? = null
    var items: MutableList<DvEhrUri> = mutableListOf()

    @RequiresNotNull
    var reason: DvText? = null
    var isPending = false
}