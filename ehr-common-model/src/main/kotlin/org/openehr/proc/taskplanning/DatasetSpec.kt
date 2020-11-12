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

package org.openehr.proc.taskplanning

import care.better.platform.proc.taskplanning.visitor.TaskModelVisitor
import care.better.platform.proc.taskplanning.visitor.VisitableByModelVisitor
import org.openehr.rm.common.Locatable
import org.openehr.rm.datastructures.ItemStructure
import java.io.Serializable

/**
 * @author Primoz Delopst
 */
abstract class DatasetSpec() : Locatable(), Serializable, VisitableByModelVisitor {

    var formId: String? = null
    var templateId: String? = null
    var otherDetails: ItemStructure? = null
    var populatingCall: SystemCall? = null
    var formSectionPath: String? = null

    constructor(formId: String?, templateId: String?) : this() {
        this.formId = formId
        this.templateId = templateId
    }

    constructor(formId: String?, templateId: String?, otherDetails: ItemStructure?) : this(formId, templateId) {
        this.otherDetails = otherDetails
    }

    override fun accept(visitor: TaskModelVisitor) {
        visitor.visit(this)
        visitor.afterVisit(this)
        populatingCall?.also { it.accept(visitor) }
        visitor.afterAccept(this)
    }

    override fun toString(): String =
            "DatasetSpec{" +
                    "formId='$formId'" +
                    ", templateId='$templateId'" +
                    ", otherDetails=$otherDetails" +
                    ", populatingCall=$populatingCall" +
                    ", formSectionPath=$formSectionPath" +
                    '}'
}