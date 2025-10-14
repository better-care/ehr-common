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

package org.openehr.rm.common

import care.better.openehr.rm.RmObject
import care.better.platform.annotation.Open
import care.better.platform.annotation.Required
import jakarta.xml.bind.annotation.XmlAccessType
import jakarta.xml.bind.annotation.XmlAccessorType
import jakarta.xml.bind.annotation.XmlElement
import jakarta.xml.bind.annotation.XmlType
import org.openehr.rm.datatypes.DvEhrUri
import org.openehr.rm.datatypes.DvText

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "LINK", propOrder = [
        "meaning",
        "type",
        "target"])
@Open
class Link() : RmObject(), java.io.Serializable {

    companion object {
        private const val serialVersionUID: Long = 0L

        /**
         * Creates a name suffix suitable for use in LINKs (e.g. `"Order", 1` becomes `'Order #2'` and can be used like i.e. /items[at0001,&gt;&gt;'Order #2'&lt;&lt;]/...)
         *
         * @param name  name part of suffix
         * @param index element index (0-based)
         * @return suffix to be placed after node id
         */
        @JvmStatic
        fun getNameSuffix(name: String, index: Int): String = quote(name + (if (index > 0) " #" + (index + 1) else ""))

        /**
         * Quotes a name suffix suitable for use in LINKs (e.g. `"Order #2"` becomes `'Order #2'` and can be used like /items[at0001,&gt;&gt;'Order #2'&lt;&lt;]/...)
         *
         * @param name  name
         * @return suffix to be placed after node id
         */
        @JvmStatic
        fun quote(name: String): String = '\''.toString() + name.replace("\\", "\\\\").replace("\"", "\\\"").replace("'", "\\'")  + '\''
    }

    constructor(meaning: DvText, type: DvText, target: DvEhrUri) : this() {
        this.meaning = meaning
        this.type = type
        this.target = target
    }

    @XmlElement(required = true)
    @Required
    var meaning: DvText? = null

    @XmlElement(required = true)
    @Required
    var type: DvText? = null

    @XmlElement(required = true)
    @Required
    var target: DvEhrUri? = null
}
