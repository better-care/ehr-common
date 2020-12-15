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
import java.util.*

/**
 * @author Primoz Delopst
 */

@Open
abstract class DvEncapsulated : DataValue() {
    companion object {
        @JvmStatic
        private val serialVersionUID: Long = 0L
    }

    var charset: CodePhrase? = null
    var language: CodePhrase? = null

    override fun equals(other: Any?): Boolean =
            when {
                this === other -> true
                javaClass != other?.javaClass -> false
                (other as DvEncapsulated).charset != charset -> false
                else -> language == other.language
            }

    override fun hashCode(): Int = Objects.hash(charset, language)
}