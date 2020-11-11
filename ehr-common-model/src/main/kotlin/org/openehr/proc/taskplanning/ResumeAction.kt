package org.openehr.proc.taskplanning

import care.better.openehr.rm.RmObject
import org.openehr.base.basetypes.UidBasedId
import java.io.Serializable

/**
 * @author Primoz Delopst
 */
class ResumeAction : RmObject, Serializable {

    lateinit var resumeType: ResumeType
    var resumeLocation: UidBasedId? = null

    constructor() : super()

    constructor(resumeType: ResumeType) : this() {
        this.resumeType = resumeType
    }

    constructor(resumeType: ResumeType, resumeLocation: UidBasedId?) : this(resumeType) {
        this.resumeLocation = resumeLocation
    }

    override fun toString(): String =
            "ResumeAction{" +
                    "resumeType=$resumeType" +
                    ", resumeLocation=$resumeLocation" +
                    '}'
}