package org.openehr.proc.taskplanning

import care.better.platform.annotation.RequiresNotNull
import org.openehr.rm.datastructures.ItemStructure

/**
 * @author Primoz Delopst
 */
class SystemNotification : PlanEvent {

    @RequiresNotNull
    var systemId: String? = null
    var notificationType: String? = null
    var referenceId: String? = null

    constructor()

    constructor(systemId: String?) {
        this.systemId = systemId
    }

    constructor(systemId: String?, notificationType: String?, referenceId: String?) : this(null, systemId, notificationType, referenceId)

    constructor(itemStructure: ItemStructure?, systemId: String?, notificationType: String?, referenceId: String?) : super(itemStructure) {
        this.systemId = systemId
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