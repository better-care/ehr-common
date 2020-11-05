package org.openehr.rm.common

import care.better.openehr.referencemodel.RmObject
import kotlinx.serialization.Serializable
import org.openehr.rm.datatypes.DvEncapsulated
import org.openehr.rm.datatypes.DvIdentifier

/**
 * @author Primoz Delopst
 */

@Serializable
class FeederAudit : RmObject() {
    var originatingSystemItemIds: MutableList<DvIdentifier> = mutableListOf()
    var feederSystemItemIds: MutableList<DvIdentifier> = mutableListOf()
    var originalContent: DvEncapsulated? = null
    lateinit var originatingSystemAudit: FeederAuditDetails
    var feederSystemAudit: FeederAuditDetails? = null
}