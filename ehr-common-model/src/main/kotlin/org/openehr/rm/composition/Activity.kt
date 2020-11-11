package org.openehr.rm.composition

import org.openehr.rm.common.Locatable
import org.openehr.rm.datastructures.ItemStructure
import org.openehr.rm.datatypes.DvParsable

/**
 * @author Primoz Delopst
 */

class Activity : Locatable() {
    lateinit var description: ItemStructure
    var timing: DvParsable? = null
    lateinit var actionArchetypeId: String
}