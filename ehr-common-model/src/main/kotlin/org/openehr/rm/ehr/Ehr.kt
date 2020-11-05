package org.openehr.rm.ehr

import care.better.openehr.rm.RmObject
import kotlinx.serialization.Serializable
import org.openehr.base.basetypes.HierObjectId
import org.openehr.rm.datatypes.DvDateTime

/**
 * @author Primoz Delopst
 */

@Serializable
class Ehr : RmObject() {
    var systemId: HierObjectId? = null
    var ehrId: HierObjectId? = null
    var timeCreated: DvDateTime? = null
    var ehrStatus: EhrStatus? = null
}