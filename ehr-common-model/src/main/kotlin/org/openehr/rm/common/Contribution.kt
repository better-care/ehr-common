package org.openehr.rm.common

import care.better.openehr.referencemodel.RmObject
import kotlinx.serialization.Serializable
import org.openehr.base.basetypes.HierObjectId

/**
 * @author Primoz Delopst
 */

@Serializable
class Contribution : RmObject() {
    lateinit var uid: HierObjectId
    var versions: List<RmObject> = mutableListOf()
    lateinit var audit: AuditDetails
}