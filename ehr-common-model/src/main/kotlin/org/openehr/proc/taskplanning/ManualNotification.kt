package org.openehr.proc.taskplanning

import care.better.platform.annotation.RequiresNotNull
import org.openehr.rm.datastructures.ItemStructure

/**
 * @author Primoz Delopst
 */
class ManualNotification : PlanEvent {

    @RequiresNotNull
    var description: String? = null

    constructor()

    constructor(description: String?) {
        this.description = description
    }

    constructor(otherDetails: ItemStructure?, description: String?) : super(otherDetails) {
        this.description = description
    }

    override fun toString(): String =
            "ManualNotification{" +
                    "description='$description'" +
                    "} ${super.toString()}"
}