package org.openehr.rm.composition

import org.openehr.rm.datastructures.ItemStructure
import org.openehr.rm.datatypes.DvDateTime

/**
 * @author Primoz Delopst
 */

class Action : CareEntry() {
    lateinit var time: DvDateTime
    lateinit var description: ItemStructure
    lateinit var ismTransition: IsmTransition
    var instructionDetails: InstructionDetails? = null
}