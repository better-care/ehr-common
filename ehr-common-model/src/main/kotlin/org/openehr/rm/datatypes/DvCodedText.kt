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
import java.util.*

/**
 * @author Primoz Delopst
 */

@Open
class DvCodedText : DvText() {
    companion object {
        /**
         * Creates a [DvCodedText] from terminology id, code and value
         *
         * @param terminology terminology id
         * @param code        code
         * @param value       value
         * @return [DvCodedText] object
         */
        @JvmStatic
        fun create(terminology: String, code: String, value: String?): DvCodedText =
                DvCodedText().apply {
                    this.definingCode = CodePhrase.create(terminology, code)
                    this.value = value
                }

        /**
         * Creates a [DvCodedText] with local terminology, code and value
         *
         * @param code  code
         * @param value value
         * @return [DvCodedText] object
         */
        @JvmStatic
        fun createWithLocalTerminology(code: String, value: String?): DvCodedText = create("local", code, value)

        /**
         * Creates a [DvCodedText] with openEHR terminology, code and value
         *
         * @param code  code
         * @param value value
         * @return [DvCodedText] object
         */
        @JvmStatic
        fun createWithOpenEHRTerminology(code: String, value: String?): DvCodedText = create("openehr", code, value)
    }

    @Required
    var definingCode: CodePhrase? = null

    override fun equals(other: Any?): Boolean =
            when {
                this === other -> true
                javaClass != other?.javaClass -> false
                else -> (other as DvCodedText).definingCode == definingCode
            }

    override fun hashCode(): Int = Objects.hash(definingCode)
}