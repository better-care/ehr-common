package org.openehr.rm.composition

import care.better.openehr.rm.RmObject
import kotlinx.serialization.Serializable
import org.openehr.base.basetypes.LocatableRef
import org.openehr.rm.datastructures.ItemStructure

/**
 * @author Primoz Delopst
 */

@Serializable
class InstructionDetails  : RmObject() {
    lateinit var instructionId: LocatableRef
    lateinit var activityId: String
    var wfDetails: ItemStructure? = null
}