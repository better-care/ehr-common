package org.openehr.proc.taskplanning

import care.better.platform.proc.taskplanning.visitor.TaskModelVisitor
import care.better.platform.proc.taskplanning.visitor.VisitableByModelVisitor
import org.openehr.base.basetypes.LocatableRef
import org.openehr.rm.common.Locatable
import java.io.Serializable

/**
 * @author Primoz Delopst
 */
class OrderRef() : Locatable(), Serializable, VisitableByModelVisitor {

    var orderTag: String? = null
    var instructionArchetypeId: String? = null
    var actionArchetypeId: String? = null
    var orderRef: LocatableRef? = null

    override fun accept(visitor: TaskModelVisitor) {
        visitor.visit(this)
        visitor.afterVisit(this)
        visitor.afterAccept(this)
    }

    override fun toString(): String =
            "OrderRef{" +
                    "orderTag='$orderTag'" +
                    ", instructionArchetypeId='$instructionArchetypeId'" +
                    ", actionArchetypeId='$actionArchetypeId'" +
                    ", orderRef=$orderRef" +
                    "} ${super.toString()}"
}