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
import org.openehr.rm.datatypes.DvCodedText
import org.openehr.rm.datatypes.DvInterval
import org.openehr.rm.datatypes.DvText
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
    name = "PARTICIPATION", propOrder = [
        "function",
        "performer",
        "time",
        "mode"])
@Open
class Participation() : RmObject(), Serializable {
    companion object {
        private const val serialVersionUID: Long = 0L
    }

    @JvmOverloads
    constructor(function: DvText, performer: PartyProxy, time: DvInterval? = null, mode: DvCodedText? = null) : this(){
        this.function = function
        this.performer = performer
        this.time = time
        this.mode = mode
    }

    @XmlElement(required = true)
    @Required
    var function: DvText? = null

    @XmlElement(required = true)
    @Required
    var performer: PartyProxy? = null

    var time: DvInterval? = null

    var mode: DvCodedText? = null
}
