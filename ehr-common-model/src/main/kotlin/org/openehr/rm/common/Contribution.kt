package org.openehr.rm.common

import care.better.openehr.rm.RmObject
import care.better.platform.annotation.RequiresNotEmpty
import org.openehr.base.basetypes.HierObjectId
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

class Contribution : RmObject(), Serializable {
    lateinit var uid: HierObjectId
    @RequiresNotEmpty
    var versions: MutableList<RmObject> = mutableListOf()
    lateinit var audit: AuditDetails
}