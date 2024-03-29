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

package org.openehr.rm.composition

import care.better.platform.annotation.Open
import org.openehr.base.basetypes.ObjectRef
import org.openehr.rm.datastructures.ItemStructure
import javax.xml.bind.annotation.*

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "CARE_ENTRY", propOrder = [
        "protocol",
        "guidelineId"])
@XmlSeeAlso(value = [Evaluation::class, Observation::class, Instruction::class, Action::class])
@Open
abstract class CareEntry : Entry() {
    companion object {
        private const val serialVersionUID: Long = 0L
    }

    var protocol: ItemStructure? = null

    @XmlElement(name = "guideline_id")
    var guidelineId: ObjectRef? = null
}
