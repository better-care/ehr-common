package org.openehr.proc.taskplanning

import care.better.platform.proc.taskplanning.visitor.TaskModelVisitor
import org.openehr.rm.datastructures.ItemStructure

/**
 * @author Primoz Delopst
 */

class ReviewDatasetSpec : DatasetSpec {

    var captureDatasets: MutableList<CaptureDatasetSpec> = mutableListOf()

    constructor()

    constructor(formId: String?, templateId: String?) : super(formId, templateId)

    constructor(formId: String?, templateId: String?, otherDetails: ItemStructure?) : super(formId, templateId, otherDetails)

    override fun accept(visitor: TaskModelVisitor) {
        visitor.visit(this)
        visitor.afterVisit(this)
        populatingCall?.also { it.accept(visitor) }
        captureDatasets.forEach { it.accept(visitor) }
        visitor.afterAccept(this)
    }

    override fun toString(): String =
            "ReviewDatasetSpec{" +
                    "populatingCall=$populatingCall" +
                    ", captureDatasets=$captureDatasets" +
                    "} ${super.toString()}"
}