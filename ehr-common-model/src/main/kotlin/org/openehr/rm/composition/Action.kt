package org.openehr.rm.composition

import care.better.platform.annotation.RequiresNotNull
import org.openehr.rm.datastructures.ItemStructure
import org.openehr.rm.datatypes.DvDateTime

/**
 * @author Primoz Delopst
 */

class Action : CareEntry() {
    @RequiresNotNull
    var time: DvDateTime? = null

    @RequiresNotNull
    var description: ItemStructure? = null

    @RequiresNotNull
    var ismTransition: IsmTransition? = null
    var instructionDetails: InstructionDetails? = null
}