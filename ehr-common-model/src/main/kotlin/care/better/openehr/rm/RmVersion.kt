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

package care.better.openehr.rm

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */
enum class RmVersion(val version: String, private val possibleValues: Set<String>) {

    RM1_0_1("1.0.1", setOf("1.0.1", "RM1.0.1", "rm1.0.1", "1_0_1", "RM1_0_1", "rm1_0_1")),
    RM1_0_2("1.0.2", setOf("1.0.2", "RM1.0.2", "rm1.0.2", "1_0_2", "RM1_0_2", "rm1_0_2")),
    RM1_0_3("1.0.3", setOf("1.0.3", "RM1.0.3", "rm1.0.3", "1_0_3", "RM1_0_3", "rm1_0_3")),
    RM1_0_4("1.0.4", setOf("1.0.4", "RM1.0.4", "rm1.0.4", "1_0_4", "RM1_0_4", "rm1_0_4"));

    companion object {
        fun from(version: String): RmVersion =
            when {
                RM1_0_1.possibleValues.contains(version) -> RM1_0_1
                RM1_0_2.possibleValues.contains(version) -> RM1_0_2
                RM1_0_3.possibleValues.contains(version) -> RM1_0_3
                RM1_0_4.possibleValues.contains(version) -> RM1_0_4
                else -> throw IllegalArgumentException("Unknown RM version $version.")
            }
    }
}
