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

package org.openehr.rm.common

import care.better.openehr.rm.RmObject
import care.better.platform.annotation.Opened
import care.better.platform.annotation.Required
import org.openehr.rm.datatypes.DvEhrUri
import org.openehr.rm.datatypes.DvText
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

@Opened
class Link : RmObject(), Serializable {

    companion object {
        /**
         * Creates a name suffix suitable for use in LINKs (i.e. /items[at0001,&gt;&gt;'Order #2'&lt;&lt;]/...)
         *
         * @param name  name part of suffix
         * @param index element index (0-based)
         * @return complete suffix to be placed after node id
         */
        @JvmStatic
        fun getNameSuffix(name: String, index: Int): String = '\''.toString() + quote(name) + (if (index > 0) " #" + (index + 1) else "") + '\''

        @JvmStatic
        fun quote(parameter: String): String = parameter.replace("\\", "\\\\").replace("\"", "\\\"").replace("'", "\\'")
    }

    @Required
    var meaning: DvText? = null

    @Required
    var type: DvText? = null

    @Required
    var target: DvEhrUri? = null
}