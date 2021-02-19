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

package care.better.platform

import care.better.openehr.processmodel.taskplanning.TpVersion
import care.better.openehr.rm.RmVersion

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */

class ModelVersion(val rmVersion: RmVersion, val tpVersion: TpVersion?) {

    constructor(rmVersion: RmVersion) : this(rmVersion, null)

    companion object {
        @JvmField
        val CURRENT_VERSION = ModelVersion(RmVersion.RM1_0_4, TpVersion.TP1_5_1)

        @JvmField
        val LEGACY_VERSION = ModelVersion(RmVersion.RM1_0_2)

        @JvmField
        val CURRENT_VERSION_STRING = CURRENT_VERSION.toString()

        @JvmStatic
        fun from(versionString: String): ModelVersion = with(versionString.split(",")) {
            if (this.size > 1) {
                ModelVersion(RmVersion.from(this[0]), TpVersion.from(this[1]))
            } else {
                ModelVersion(RmVersion.from(this[0]))
            }
        }
    }

    override fun toString(): String = "${rmVersion.version}${tpVersion?.let { ",${tpVersion.version}" } ?: ""}"
}
