package org.openehr.proc.taskplanning

import org.openehr.rm.datastructures.ItemStructure

/**
 * @author Primoz Delopst
 */
class ManualNotification : PlanEvent {

    lateinit var description: String

    constructor() : super()

    constructor(description: String) : this() {
        this.description = description
    }

    constructor(otherDetails: ItemStructure?, description: String) : super(otherDetails) {
        this.description = description
    }

    override fun toString(): String =
            "ManualNotification{" +
                    "description='$description'" +
                    "} ${super.toString()}"
}