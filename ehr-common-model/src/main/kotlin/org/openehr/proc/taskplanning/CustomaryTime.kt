package org.openehr.proc.taskplanning

import care.better.platform.annotation.RequiresNotNull
import org.openehr.rm.datatypes.DvCodedText

/**
 * @author Primoz Delopst
 */
class CustomaryTime() : TimeSpecifier() {

    @RequiresNotNull
    var time: DvCodedText? = null

    constructor(time: DvCodedText?) : this() {
        this.time = time
    }

    override fun toString(): String =
            "CustomaryTime{" +
                    "time=$time" +
                    "} ${super.toString()}"
}