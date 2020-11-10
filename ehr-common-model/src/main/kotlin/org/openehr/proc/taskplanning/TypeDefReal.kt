package org.openehr.proc.taskplanning

import org.openehr.base.basetypes.ObjectRef

/**
 * @author Primoz Delopst
 */

class TypeDefReal : ExprTypeDef<Double>("Real") {

    companion object {
        val INSTANCE: TypeDefReal = TypeDefReal()
    }

    override fun toString(): String = "TypeDefReal{} ${super.toString()}"
}