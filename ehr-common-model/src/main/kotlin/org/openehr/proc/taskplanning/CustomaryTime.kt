package org.openehr.proc.taskplanning

import org.openehr.rm.datatypes.DvCodedText

/**
 * @author Primoz Delopst
 */
class CustomaryTime() : TimeSpecifier() {

    lateinit var time: DvCodedText

    constructor(time: DvCodedText) : this() {
        this.time = time
    }

    override fun toString(): String =
            "CustomaryTime{" +
                    "time=$time" +
                    "} ${super.toString()}"
}