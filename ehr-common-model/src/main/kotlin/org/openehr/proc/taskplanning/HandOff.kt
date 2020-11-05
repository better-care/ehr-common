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

import care.better.platform.annotation.Open
import care.better.platform.proc.taskplanning.visitor.TaskModelVisitor
import org.openehr.base.basetypes.UidBasedId
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlType

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */
@XmlType(name = "HAND_OFF", propOrder = ["target", "targetUid"])
@Open
class HandOff() : DispatchableAction(), LinkedPlan {
    companion object {
        private const val serialVersionUID: Long = 0L
    }

    @XmlElement
    private var target: TaskPlan? = null

    @XmlElement(name = "target_uid")
    private var targetUid: UidBasedId? = null

    constructor(target: TaskPlan?) : this() {
        this.target = target
    }

    constructor(targetUid: UidBasedId?) : this() {
        this.targetUid = targetUid
    }

    override fun getTarget(): TaskPlan? = target

    override fun setTarget(target: TaskPlan?) {
        this.target = target
    }

    override fun getTargetUid(): UidBasedId? = targetUid

    override fun setTargetUid(targetUid: UidBasedId?) {
        this.targetUid = targetUid
    }

    override fun accept(visitor: TaskModelVisitor) {
        visitor.visit(this)
        visitor.afterVisit(this)
        acceptPreconditions(visitor)
        target?.also { it.accept(visitor) }
        visitor.afterAccept(this)
    }

    override fun toString(): String =
        "HandOff{" +
                "target=$target" + target +
                "targetUid=$targetUid" + targetUid +
                "} ${super.toString()}"
}
