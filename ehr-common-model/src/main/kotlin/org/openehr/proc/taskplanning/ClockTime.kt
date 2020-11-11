package org.openehr.proc.taskplanning

/**
 * @author Primoz Delopst
 */

class ClockTime() : TimeSpecifier() {
    lateinit var time: String

    constructor(time: String) : this() {
        this.time = time
    }

    override fun toString(): String =
            "ClockTime{" +
                    "time=$time" +
                    "} ${super.toString()}"
}