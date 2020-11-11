package org.openehr.proc.taskplanning

import org.openehr.base.basetypes.UidBasedId

/**
 * @author Primoz Delopst
 */
interface LinkedPlan {
    fun getTarget(): TaskPlan?

    fun setTarget(target: TaskPlan?)

    fun getTargetUid(): UidBasedId?

    fun setTargetUid(targetUid: UidBasedId?)
}