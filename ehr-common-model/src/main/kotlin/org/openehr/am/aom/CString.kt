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

package org.openehr.am.aom

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlType

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "C_STRING", propOrder = [
        "pattern",
        "list",
        "listOpen",
        "assumedValue",
        "defaultValue"])
class CString : CPrimitive() {
    companion object {
        private const val serialVersionUID: Long = 0L
    }

    var pattern: String? = null

    var list: MutableList<String> = mutableListOf()

    @XmlElement(name = "list_open")
    var listOpen: Boolean? = null

    @XmlElement(name = "assumed_value")
    var assumedValue: String? = null

    @XmlElement(name = "default_value")
    var defaultValue: String? = null
}
