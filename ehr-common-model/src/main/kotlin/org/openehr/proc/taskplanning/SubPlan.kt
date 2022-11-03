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
import org.openehr.base.basetypes.LocatableRef
import org.openehr.base.basetypes.UidBasedId
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlType


/**
 * @author Primoz Delopst
 * @since 3.1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SUB_PLAN", propOrder = ["target", "targetUid"])
@Open
class SubPlan : PerformableAction, LinkedPlan {
    companion object {
        private const val serialVersionUID: Long = 0L
    }

    @XmlElement
    private var target: TaskPlan? = null

    @XmlElement(name = "target_uid")
    private var targetUid: UidBasedId? = null

    constructor()

    constructor(target: TaskPlan?) {
        this.target = target
    }

    constructor(targetUid: UidBasedId?) {
        this.targetUid = targetUid
    }

    constructor(instructionActivity: LocatableRef?, target: TaskPlan?) : super(instructionActivity) {
        this.target = target
    }

    constructor(instructionActivity: LocatableRef?, costingData: TaskCosting?, target: TaskPlan?) : super(instructionActivity, costingData) {
        this.target = target
    }

    constructor(instructionActivity: LocatableRef?, costingData: TaskCosting?, targetUid: UidBasedId?) : super(instructionActivity, costingData) {
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
        val visited = visitor.visit(this)
        visitor.afterVisit(this)
        if (visited) {
            acceptPreconditions(visitor)
            acceptOtherParticipations(visitor)
            acceptResources(visitor)
            target?.also { it.accept(visitor) }
        }
        visitor.afterAccept(this)
    }

    override fun toString(): String =
        "SubPlan{" +
                "target=$target" +
                "targetUid=$targetUid" +
                "} ${super.toString()}"
}
