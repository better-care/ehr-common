package org.openehr.proc.taskplanning

import care.better.openehr.rm.RmObject
import care.better.platform.annotation.RequiresNotNull
import org.openehr.base.basetypes.UidBasedId
import java.io.Serializable

/**
 * @author Primoz Delopst
 */
class ResumeAction() : RmObject(), Serializable {

    @RequiresNotNull
    var resumeType: ResumeType? = null
    var resumeLocation: UidBasedId? = null

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