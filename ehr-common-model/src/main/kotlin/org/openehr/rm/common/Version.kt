package org.openehr.rm.common

import care.better.openehr.rm.RmObject
import care.better.platform.annotation.RequiresNotNull
import org.openehr.base.basetypes.ObjectRef
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

abstract class Version : RmObject(), Serializable {
    @RequiresNotNull
    var contribution: ObjectRef? = null

    @RequiresNotNull
    var commitAudit: AuditDetails? = null
    var signature: String? = null
}