package org.openehr.rm.common

import care.better.openehr.referencemodel.RmObject
import kotlinx.serialization.Serializable
import org.openehr.rm.datatypes.DvCodedText
import org.openehr.rm.datatypes.DvDateTime
import org.openehr.rm.datatypes.DvText

/**
 * @author Primoz Delopst
 */

@Serializable
open class AuditDetails : RmObject() {
    lateinit var systemId: String
    lateinit var committer: PartyProxy
    lateinit var timeCommitted: DvDateTime
    lateinit var changeType: DvCodedText
    var description: DvText? = null
}