package org.openehr.rm.composition

import care.better.openehr.rm.RmObject
import care.better.platform.annotation.RequiresNotNull
import org.openehr.base.basetypes.LocatableRef
import org.openehr.rm.datastructures.ItemStructure
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

class InstructionDetails : RmObject(), Serializable {
    @RequiresNotNull
    var instructionId: LocatableRef? = null

    @RequiresNotNull
    var activityId: String? = null
    var wfDetails: ItemStructure? = null
}