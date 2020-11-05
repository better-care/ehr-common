package org.openehr.rm.common

import care.better.openehr.referencemodel.RmObject
import kotlinx.serialization.Serializable
import org.openehr.base.basetypes.ObjectRef

/**
 * @author Primoz Delopst
 */

@Serializable
abstract class Version : RmObject() {
    lateinit var contribution: ObjectRef
    lateinit var commitAudit: AuditDetails
    var signature: String? = null
}