package org.openehr.rm.datastructures

import kotlinx.serialization.Serializable
import org.openehr.rm.common.Locatable
import org.openehr.rm.datatypes.DvDateTime

/**
 * @author Primoz Delopst
 */

@Serializable
abstract class Event : Locatable() {
    lateinit var time: DvDateTime
    lateinit var data: ItemStructure
    var state: ItemStructure? = null
}