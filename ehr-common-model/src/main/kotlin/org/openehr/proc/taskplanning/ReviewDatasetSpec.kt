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

package org.openehr.proc.taskplanning

import care.better.platform.annotation.Open
import care.better.platform.proc.taskplanning.visitor.TaskModelVisitor
import org.openehr.rm.datastructures.ItemStructure
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlType

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "REVIEW_DATASET_SPEC", propOrder = ["captureDatasets"])
@Open
class ReviewDatasetSpec : DatasetSpec {
    companion object {
        private const val serialVersionUID: Long = 0L
    }

    @XmlElement(name = "capture_datasets")
    var captureDatasets: MutableList<CaptureDatasetSpec> = mutableListOf()

    constructor()

    constructor(formId: String?, templateId: String?) : super(formId, templateId)

    constructor(formId: String?, templateId: String?, otherDetails: ItemStructure?) : super(formId, templateId, otherDetails)

    override fun accept(visitor: TaskModelVisitor) {
        val visited = visitor.visit(this)
        visitor.afterVisit(this)
        if (visited) {
            populatingCall?.also { it.accept(visitor) }
            captureDatasets.forEach { it.accept(visitor) }
        }
        visitor.afterAccept(this)
    }

    override fun toString(): String =
        "ReviewDatasetSpec{" +
                "populatingCall=$populatingCall" +
                ", captureDatasets=$captureDatasets" +
                "} ${super.toString()}"
}
