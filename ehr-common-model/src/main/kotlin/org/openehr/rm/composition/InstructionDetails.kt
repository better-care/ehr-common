package org.openehr.rm.composition

import care.better.openehr.rm.RmObject
import org.openehr.base.basetypes.LocatableRef
import org.openehr.rm.datastructures.ItemStructure
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

class InstructionDetails : RmObject(), Serializable {
    lateinit var instructionId: LocatableRef
    lateinit var activityId: String
    var wfDetails: ItemStructure? = null
}