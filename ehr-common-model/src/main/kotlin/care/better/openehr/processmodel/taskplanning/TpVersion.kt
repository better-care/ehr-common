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

package care.better.openehr.processmodel.taskplanning

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */

enum class TpVersion(val version: String, private val possibleValues: Set<String>) {

    TP1_5_0("tp1.5.0", setOf("tp1.5.0", "TP1.5.0", "tp1_5_0", "TP1_5_0")),
    TP1_5_1("tp1.5.1", setOf("tp1.5.1", "TP1.5.1", "tp1_5_1", "TP1_5_1"));

    companion object {
        fun from(version: String): TpVersion =
            when {
                TP1_5_0.possibleValues.contains(version) -> TP1_5_0
                TP1_5_1.possibleValues.contains(version) -> TP1_5_1
                else -> throw IllegalArgumentException("Unknown TP model version $version.")
            }
    }
}
