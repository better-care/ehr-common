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

import care.better.platform.annotation.Required
import java.util.*

/**
 * @author Primoz Delopst
 */

class DvIdentifier : DataValue() {
    var issuer: String? = null
    var assigner: String? = null

    @Required
    var id: String? = null
    var type: String? = null

    override fun equals(other: Any?): Boolean =
            when {
                this === other -> true
                javaClass != other?.javaClass -> false
                (other as DvIdentifier).id != id -> false
                other.assigner != assigner -> false
                other.issuer != issuer -> false
                else -> other.type == type
            }

    override fun hashCode(): Int = Objects.hash(id, type, issuer, assigner)
}