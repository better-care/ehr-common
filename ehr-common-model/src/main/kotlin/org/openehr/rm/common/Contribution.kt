package org.openehr.rm.common

import care.better.openehr.rm.RmObject
import care.better.platform.annotation.RequiresNotEmpty
import care.better.platform.annotation.RequiresNotNull
import org.openehr.base.basetypes.HierObjectId
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

class Contribution : RmObject(), Serializable {
    @RequiresNotNull
    var uid: HierObjectId? = null

    @RequiresNotEmpty
    var versions: MutableList<RmObject> = mutableListOf()

    @RequiresNotNull
    var audit: AuditDetails? = null
}