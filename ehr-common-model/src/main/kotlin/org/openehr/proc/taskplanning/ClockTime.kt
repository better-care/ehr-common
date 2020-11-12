package org.openehr.proc.taskplanning

import care.better.platform.annotation.RequiresNotNull

/**
 * @author Primoz Delopst
 */

class ClockTime() : TimeSpecifier() {
    @RequiresNotNull
    var time: String? = null

    constructor(time: String?) : this() {
        this.time = time
    }

    override fun toString(): String =
            "ClockTime{" +
                    "time=$time" +
                    "} ${super.toString()}"
}