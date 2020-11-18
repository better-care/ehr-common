/* Copyright 2020-2025 Better Ltd (www.better.care)
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

package org.openehr.rm.datatypes

/**
 * @author Primoz Delopst
 */

class DvEhrUri : DvUri() {
    companion object {
        /**
         * Creates [DvEhrUri].
         *
         * @param ehrUid         ehr uid
         * @param compositionUid composition uid - this can be full uid (xxx::system_id::version) or just versioned uid (xxx). If full path is specified then link
         * points to a specific composition version, otherwise it points to the last version.
         * @return [DvEhrUri] object
         */
        @JvmStatic
        fun create(ehrUid: String, compositionUid: String): DvEhrUri = create(ehrUid, compositionUid, null)

        /**
         * Creates [DvEhrUri].
         *
         * @param ehrUid         ehr uid
         * @param compositionUid composition uid - this can be full uid (xxx::system_id::version) or just versioned uid (xxx). If full path is specified then link
         * points to a specific composition version, otherwise it points to the last version.
         * @param path           RM path to an element within a composition
         * @return [DvEhrUri] object
         */
        @JvmStatic
        fun create(ehrUid: String, compositionUid: String, path: String?): DvEhrUri =
                DvEhrUri().apply {
                    this.value = "ehr://$ehrUid/$compositionUid${path?.let { if (it.startsWith("/")) it else "/$it" } ?: ""}"
                }

    }
}