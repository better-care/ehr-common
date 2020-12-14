/* Copyright 2021 Better Ltd (www.better.care)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openehr.proc.taskplanning

import care.better.openehr.rm.RmObject
import care.better.platform.annotation.Open
import care.better.platform.annotation.Required
import org.openehr.base.basetypes.UidBasedId
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

@Open
class ResumeAction() : RmObject(), Serializable {

    @Required
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