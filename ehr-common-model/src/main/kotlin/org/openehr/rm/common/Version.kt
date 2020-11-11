package org.openehr.rm.common

import care.better.openehr.rm.RmObject
import org.openehr.base.basetypes.ObjectRef
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

abstract class Version : RmObject(), Serializable {
    lateinit var contribution: ObjectRef
    lateinit var commitAudit: AuditDetails
    var signature: String? = null
}