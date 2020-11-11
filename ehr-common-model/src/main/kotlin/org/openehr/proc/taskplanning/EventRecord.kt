package org.openehr.proc.taskplanning

import care.better.openehr.rm.RmObject
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

abstract class EventRecord constructor() : RmObject(), Serializable {
    lateinit var time: String
    var description: String? = null

    constructor(time: String) : this() {
        this.time = time
    }

    constructor(time: String, description: String?) : this(time) {
        this.description = description
    }

    override fun toString(): String =
            "EventRecord{" +
                    "time=$time" +
                    ", description='$description'" +
                    '}'
}