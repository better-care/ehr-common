package org.openehr.rm.common

import care.better.openehr.rm.RmObject
import kotlinx.serialization.Serializable
import org.openehr.base.basetypes.HierObjectId

/**
 * @author Primoz Delopst
 */

@Serializable
class Contribution : RmObject() {
    lateinit var uid: HierObjectId
    var versions: MutableList<RmObject> = mutableListOf()
    lateinit var audit: AuditDetails
}