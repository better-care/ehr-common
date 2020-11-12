package org.openehr.proc.taskplanning

import org.openehr.base.basetypes.UidBasedId

/**
 * @author Primoz Delopst
 */

fun interface TaskReferencingEvent {
    fun getTaskId(): UidBasedId?
}