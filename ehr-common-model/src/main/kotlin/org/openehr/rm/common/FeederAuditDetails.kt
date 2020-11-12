package org.openehr.rm.common

import care.better.openehr.rm.RmObject
import care.better.platform.annotation.RequiresNotNull
import org.openehr.rm.datatypes.DvDateTime
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

class FeederAuditDetails : RmObject(), Serializable {
    @RequiresNotNull
    var systemId: String? = null
    var location: PartyIdentified? = null
    var provider: PartyIdentified? = null
    var subject: PartyProxy? = null
    var time: DvDateTime? = null
    var versionId: String? = null
}