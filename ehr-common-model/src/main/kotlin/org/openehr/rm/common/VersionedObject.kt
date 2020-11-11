package org.openehr.rm.common

import care.better.openehr.rm.RmObject
import org.openehr.base.basetypes.HierObjectId
import org.openehr.base.basetypes.ObjectRef
import org.openehr.rm.datatypes.DvCodedText
import org.openehr.rm.datatypes.DvDateTime
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

class VersionedObject : RmObject(), Serializable {
    var uid: HierObjectId? = null
    var ownerId: ObjectRef? = null
    var timeCreated: DvDateTime? = null
    var trunkLifecycleState: DvCodedText? = null
}