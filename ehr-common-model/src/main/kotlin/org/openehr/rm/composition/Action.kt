package org.openehr.rm.composition

import kotlinx.serialization.Serializable
import org.openehr.rm.datastructures.ItemStructure
import org.openehr.rm.datatypes.DvDateTime

/**
 * @author Primoz Delopst
 */

@Serializable
class Action : CareEntry() {
    lateinit var time: DvDateTime
    lateinit var description: ItemStructure
    lateinit var ismTransition: IsmTransition
    var instructionDetails: InstructionDetails? = null
}