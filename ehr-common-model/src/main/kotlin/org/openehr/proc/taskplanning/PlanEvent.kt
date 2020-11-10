package org.openehr.proc.taskplanning

import org.openehr.rm.common.Locatable
import org.openehr.rm.datastructures.ItemStructure

/**
 * @author Primoz Delopst
 */
abstract class PlanEvent constructor() : Locatable() {

    var otherDetails: ItemStructure? = null
    var delay: String? = null

    protected constructor(otherDetails: ItemStructure?) : this() {
        this.otherDetails = otherDetails
    }

    override fun toString(): String =
            "PlanEvent{" +
                    "otherDetails=$otherDetails" +
                    ", delay='$delay" +
                    ", uid=$uid" +
                    '}'
}