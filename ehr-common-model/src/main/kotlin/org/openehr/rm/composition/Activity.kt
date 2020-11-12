package org.openehr.rm.composition

import care.better.platform.annotation.RequiresNotNull
import org.openehr.rm.common.Locatable
import org.openehr.rm.datastructures.ItemStructure
import org.openehr.rm.datatypes.DvParsable

/**
 * @author Primoz Delopst
 */

class Activity : Locatable() {
    @RequiresNotNull
    var description: ItemStructure? = null
    var timing: DvParsable? = null

    @RequiresNotNull
    var actionArchetypeId: String? = null
}