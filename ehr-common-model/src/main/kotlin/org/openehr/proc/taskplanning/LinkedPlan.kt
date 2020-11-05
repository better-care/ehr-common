/* Copyright 2021 Better Ltd (www.better.care)
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

import org.openehr.base.basetypes.UidBasedId
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlSeeAlso
import javax.xml.bind.annotation.XmlType

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */
@XmlType(name = "LINKED_PLAN", propOrder = ["target", "targetUid"])
@XmlSeeAlso(value = [SubPlan::class, HandOff::class])

interface LinkedPlan {
    @XmlElement
    fun getTarget(): TaskPlan?

    fun setTarget(target: TaskPlan?)

    @XmlElement(name = "target_uid")
    fun getTargetUid(): UidBasedId?

    fun setTargetUid(targetUid: UidBasedId?)
}
