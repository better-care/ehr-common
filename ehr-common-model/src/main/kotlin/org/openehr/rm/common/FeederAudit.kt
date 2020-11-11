package org.openehr.rm.common

import care.better.openehr.rm.RmObject
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
    lateinit var originatingSystemAudit: FeederAuditDetails
    var feederSystemAudit: FeederAuditDetails? = null
}