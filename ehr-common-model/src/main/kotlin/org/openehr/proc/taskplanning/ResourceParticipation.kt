package org.openehr.proc.taskplanning

import care.better.openehr.rm.RmObject
import care.better.platform.proc.taskplanning.visitor.TaskModelVisitor
import care.better.platform.proc.taskplanning.visitor.VisitableByModelVisitor
import org.openehr.base.basetypes.ObjectRef
import org.openehr.rm.datatypes.DvText
import java.io.Serializable

/**
 * @author Primoz Delopst
 */
class ResourceParticipation() : RmObject(), Serializable, VisitableByModelVisitor {

    lateinit var resourceType: DvText
    var externalRef: ObjectRef? = null

    constructor(resourceType: DvText) : this() {
        this.resourceType = resourceType
    }

    constructor(resourceType: DvText, externalRef: ObjectRef?) : this(resourceType) {
        this.externalRef = externalRef
    }

    override fun accept(visitor: TaskModelVisitor) {
        visitor.visit(this)
        visitor.afterVisit(this)
        visitor.afterAccept(this)
    }

    override fun toString(): String =
            "ResourceParticipation{" +
                    "resourceType=$resourceType" +
                    ", externalRef=$externalRef" +
                    '}'
}