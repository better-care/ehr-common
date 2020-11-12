package org.openehr.rm.datastructures

import care.better.platform.annotation.RequiresNotNull
import org.openehr.rm.common.Locatable
import org.openehr.rm.datatypes.DvDateTime

/**
 * @author Primoz Delopst
 */

abstract class Event : Locatable() {
    @RequiresNotNull
    var time: DvDateTime? = null

    @RequiresNotNull
    var data: ItemStructure? = null
    var state: ItemStructure? = null
}