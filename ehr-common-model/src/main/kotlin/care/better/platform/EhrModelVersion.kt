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
class EhrModelVersion(vararg versions: Version<*>?) : ModelVersion(*versions) {
    companion object {
        @JvmField
        val CURRENT_VERSION = EhrModelVersion(RmVersion.RM1_1_0, TpVersion.TP1_5_1)

        @JvmField
        val LEGACY_VERSION = EhrModelVersion(RmVersion.RM1_0_2)

        @JvmField
        val CURRENT_VERSION_STRING = CURRENT_VERSION.toString()

        @JvmStatic
        fun fromRmAndTpVersionString(versionString: String): ModelVersion = with(versionString.split(",")) {
            if (this.size > 1) {
                EhrModelVersion(RmVersion.from(this[0]), TpVersion.from(this[1]))
            } else {
                EhrModelVersion(RmVersion.from(this[0]))
            }
        }
    }

    fun getRmVersion(): RmVersion? = getVersion(RmVersion::class.java)

    fun getTpVersion(): TpVersion? = getVersion(TpVersion::class.java)
}
