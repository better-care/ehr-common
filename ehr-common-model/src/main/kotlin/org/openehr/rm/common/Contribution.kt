package org.openehr.rm.common

import care.better.openehr.rm.RmObject
import org.openehr.base.basetypes.HierObjectId
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

class Contribution : RmObject(), Serializable {
    lateinit var uid: HierObjectId
    var versions: MutableList<RmObject> = mutableListOf()
    lateinit var audit: AuditDetails
}