package org.openehr.rm.common

import care.better.openehr.rm.RmObject
import care.better.platform.annotation.RequiresNotNull
import org.openehr.rm.datatypes.DvCodedText
import org.openehr.rm.datatypes.DvDateTime
import org.openehr.rm.datatypes.DvText
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

open class AuditDetails : RmObject(), Serializable {
    @RequiresNotNull
    var systemId: String? = null

    @RequiresNotNull
    var committer: PartyProxy? = null

    @RequiresNotNull
    var timeCommitted: DvDateTime? = null

    @RequiresNotNull
    var changeType: DvCodedText? = null
    var description: DvText? = null
}