package org.openehr.rm.common

import care.better.openehr.rm.RmObject
import care.better.platform.annotation.RequiresNotNull
import org.openehr.rm.datatypes.DvEncapsulated
import org.openehr.rm.datatypes.DvIdentifier
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

class FeederAudit : RmObject(), Serializable {
    var originatingSystemItemIds: MutableList<DvIdentifier> = mutableListOf()
    var feederSystemItemIds: MutableList<DvIdentifier> = mutableListOf()
    var originalContent: DvEncapsulated? = null

    @RequiresNotNull
    var originatingSystemAudit: FeederAuditDetails? = null
    var feederSystemAudit: FeederAuditDetails? = null
}