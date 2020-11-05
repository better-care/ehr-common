package org.openehr.rm.common

import care.better.openehr.rm.RmObject
import kotlinx.serialization.Serializable
import org.openehr.base.basetypes.HierObjectId
import org.openehr.base.basetypes.ObjectRef
import org.openehr.rm.datatypes.DvCodedText
import org.openehr.rm.datatypes.DvDateTime

/**
 * @author Primoz Delopst
 */

@Serializable
class VersionedObject : RmObject() {
    var uid: HierObjectId? = null
    var ownerId: ObjectRef? = null
    var timeCreated: DvDateTime? = null
    var trunkLifecycleState: DvCodedText? = null
}