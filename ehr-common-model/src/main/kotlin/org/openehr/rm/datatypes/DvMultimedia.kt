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

package org.openehr.rm.datatypes

import care.better.platform.annotation.Open
import care.better.platform.annotation.Required

/**
 * @author Primoz Delopst
 */

@Open
class DvMultimedia : DvEncapsulated() {
    companion object {
        @JvmStatic
        private val serialVersionUID: Long = 0L
    }

    var alternateText: String? = null
    var uri: DvUri? = null
    var data: ByteArray? = null

    @Required
    var mediaType: CodePhrase? = null
    var compressionAlgorithm: CodePhrase? = null
    var integrityCheck: ByteArray? = null
    var integrityCheckAlgorithm: CodePhrase? = null
    var size: Int = 0
    var thumbnail: DvMultimedia? = null
}