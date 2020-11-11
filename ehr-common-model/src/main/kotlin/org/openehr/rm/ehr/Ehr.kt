package org.openehr.rm.ehr

import care.better.openehr.rm.RmObject
import org.openehr.base.basetypes.HierObjectId
import org.openehr.rm.datatypes.DvDateTime
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

class Ehr : RmObject(), Serializable {
    var systemId: HierObjectId? = null
    var ehrId: HierObjectId? = null
    var timeCreated: DvDateTime? = null
    var ehrStatus: EhrStatus? = null
}