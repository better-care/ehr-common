package org.openehr.proc.taskplanning

import org.openehr.rm.datastructures.ItemStructure

/**
 * @author Primoz Delopst
 */
class SystemNotification : PlanEvent {

    lateinit var systemId: String
    var notificationType: String? = null
    var referenceId: String? = null

    constructor() : super()

    constructor(systemId: String) : this() {
        this.systemId = systemId
    }

    constructor(systemId: String, notificationType: String?, referenceId: String?) : this(null, systemId, notificationType, referenceId)

    constructor(itemStructure: ItemStructure?, systemId: String, notificationType: String?, referenceId: String?) : super(itemStructure) {
        this.systemId
        this.notificationType = notificationType
        this.referenceId = referenceId
    }

    override fun toString(): String =
            "SystemNotification{" +
                    "systemId='$systemId'" +
                    ", notificationType='$notificationType'" +
                    ", referenceId='$referenceId'" +
                    "} ${super.toString()}"
}