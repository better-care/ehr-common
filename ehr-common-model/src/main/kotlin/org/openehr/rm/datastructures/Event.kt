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

package org.openehr.rm.datastructures

import care.better.platform.annotation.Open
import care.better.platform.annotation.Required
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.openehr.rm.common.Locatable
import org.openehr.rm.datatypes.DvDateTime
import javax.xml.bind.annotation.*

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "EVENT", propOrder = [
    "time",
    "data",
    "state"])
@XmlSeeAlso(value = [PointEvent::class, IntervalEvent::class])
@Serializable
@SerialName("EVENT")
@Open
abstract class Event : Locatable() {
    companion object {
        @Suppress("unused")
        private const val serialVersionUID: Long = 0L
    }

    @XmlElement(required = true)
    @Required
    var time: DvDateTime? = null

    @XmlElement(required = true)
    @Required
    var data: ItemStructure? = null

    var state: ItemStructure? = null

    override fun visitProperties(ctx: care.better.platform.visitor.RmVisitorContext) {
        super.visitProperties(ctx)
        time?.visit("time", ctx)
        data?.visit("data", ctx)
        state?.visit("state", ctx)
    }
}
