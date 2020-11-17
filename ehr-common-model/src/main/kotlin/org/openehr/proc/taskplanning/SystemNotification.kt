/* Copyright 2020-2025 Better Ltd (www.better.care)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openehr.proc.taskplanning

import care.better.platform.annotation.Required
import org.openehr.rm.datastructures.ItemStructure

/**
 * @author Primoz Delopst
 */
class SystemNotification : PlanEvent {

    @Required
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