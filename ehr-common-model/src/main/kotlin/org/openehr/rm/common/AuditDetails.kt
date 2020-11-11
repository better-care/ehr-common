package org.openehr.rm.common

import care.better.openehr.rm.RmObject
import org.openehr.rm.datatypes.DvCodedText
import org.openehr.rm.datatypes.DvDateTime
import org.openehr.rm.datatypes.DvText
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

open class AuditDetails : RmObject(), Serializable {
    lateinit var systemId: String
    lateinit var committer: PartyProxy
    lateinit var timeCommitted: DvDateTime
    lateinit var changeType: DvCodedText
    var description: DvText? = null
}