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