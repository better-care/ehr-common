package org.openehr.proc.taskplanning

import care.better.openehr.rm.RmObject
import java.io.Serializable

/**
 * @author Primoz Delopst
 */
abstract class TimeSpecifier : RmObject(), Serializable {
    override fun toString(): String = "TimeSpecifier{}"
}