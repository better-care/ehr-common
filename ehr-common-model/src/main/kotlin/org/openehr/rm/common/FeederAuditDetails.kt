package org.openehr.rm.common

import care.better.openehr.rm.RmObject
import org.openehr.rm.datatypes.DvDateTime
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

class FeederAuditDetails : RmObject(), Serializable {
    lateinit var systemId: String
    var location: PartyIdentified? = null
    var provider: PartyIdentified? = null
    var subject: PartyProxy? = null
    var time: DvDateTime? = null
    var versionId: String? = null
}