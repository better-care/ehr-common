package org.openehr.proc.taskplanning

import care.better.platform.proc.taskplanning.visitor.TaskModelVisitor
import java.math.BigDecimal

/**
 * @author Primoz Delopst
 */

class ContinuousEventVariable<T> : EventVariable<T> {
    var updateVariation: BigDecimal? = null

    constructor()

    constructor(type: ExprTypeDef<T>?, name: String?) : super(type, name)

    constructor(type: ExprTypeDef<T>?, name: String?, populatingRequest: SystemCall?, updateVariation: BigDecimal?) : super(type, name, populatingRequest) {
        this.updateVariation = updateVariation
    }

    override fun accept(visitor: TaskModelVisitor) {
        visitor.visit(this)
        visitor.afterVisit(this)
        populatingRequest?.also { it.accept(visitor) }
        visitor.afterAccept(this)
    }

    override fun toString(): String =
            "ContinuousEventVariable{" +
                    "updateVariation=$updateVariation" +
                    "} ${super.toString()}"
}