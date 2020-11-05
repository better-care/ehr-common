package org.openehr.rm.common

import kotlinx.serialization.Serializable
import org.openehr.rm.datatypes.DvEhrUri
import org.openehr.rm.datatypes.DvMultimedia
import org.openehr.rm.datatypes.DvText

/**
 * @author Primoz Delopst
 */

@Serializable
class Attestation : AuditDetails() {
    var attestedView: DvMultimedia? = null
    var proof: String? = null
    var items: MutableList<DvEhrUri> = mutableListOf()
    lateinit var reason: DvText
    var isPending = false
}