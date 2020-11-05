package org.openehr.rm.common

import care.better.openehr.referencemodel.RmObject
import kotlinx.serialization.Serializable
import org.openehr.rm.datatypes.DvDateTime

/**
 * @author Primoz Delopst
 */

@Serializable
class FeederAuditDetails : RmObject() {
    lateinit var systemId: String
    var location: PartyIdentified? = null
    var provider: PartyIdentified? = null
    var subject: PartyProxy? = null
    var time: DvDateTime? = null
    var versionId: String? = null
}