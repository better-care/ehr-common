package org.openehr.rm.composition

import kotlinx.serialization.Serializable
import org.openehr.rm.common.Locatable
import org.openehr.rm.datastructures.ItemStructure
import org.openehr.rm.datatypes.DvParsable

/**
 * @author Primoz Delopst
 */

@Serializable
class Activity : Locatable() {
    lateinit var description: ItemStructure
    var timing: DvParsable? = null
    lateinit var actionArchetypeId: String
}