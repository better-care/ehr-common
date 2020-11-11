package org.openehr.proc.taskplanning

import care.better.openehr.rm.RmObject
import org.openehr.base.foundationtypes.IntervalOfInteger
import java.io.Serializable

/**
 * @author Primoz Delopst
 */
class TaskRepeat() : RmObject(), Serializable {

    var repeats: IntervalOfInteger? = null
    var terminateCondition: PlanEvent? = null
    var period: String? = null

    constructor(terminateCondition: PlanEvent?) : this() {
        this.terminateCondition = terminateCondition
    }

    constructor(repeats: IntervalOfInteger?, terminateCondition: PlanEvent?) : this(terminateCondition) {
        this.repeats = repeats
    }

    override fun toString(): String =
            "TaskRepeat{" +
                    "repeats=${intervalToStr(repeats)}" +
                    ", terminateCondition=$terminateCondition" +
                    ", period=$period" +
                    '}'

    private fun intervalToStr(repeats: IntervalOfInteger?): String? =
            repeats?.let {
                (if (it.lowerIncluded == true) '[' else '(') +
                        (if (it.lowerUnbounded) "*" else java.lang.String.valueOf(it.lower)) +
                        ".." +
                        (if (it.upperUnbounded) "*" else java.lang.String.valueOf(it.upper)) +
                        (if (it.upperIncluded == true) ']' else ')')
            }
}