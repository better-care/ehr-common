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

import care.better.openehr.rm.RmObject
import care.better.platform.annotation.Open
import care.better.platform.annotation.Required
import java.io.Serializable
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
    name = "TERM_MAPPING", propOrder = [
        "match",
        "purpose",
        "target"])
@Open
class TermMapping() : RmObject(), Serializable {
    @JvmOverloads
    constructor(match: String, purpose: DvCodedText? = null, target: CodePhrase) : this() {
        this.match = match
        this.purpose = purpose
        this.target = target
    }

    companion object {
        private const val serialVersionUID: Long = 0L
    }

    @XmlElement(required = true, defaultValue = "?")
    @Required
    var match: String? = null

    var purpose: DvCodedText? = null

    @XmlElement(required = true)
    @Required
    var target: CodePhrase? = null
}
