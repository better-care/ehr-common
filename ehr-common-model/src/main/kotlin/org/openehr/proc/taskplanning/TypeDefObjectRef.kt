package org.openehr.proc.taskplanning

import org.openehr.base.basetypes.ObjectRef

/**
 * @author Primoz Delopst
 */

class TypeDefObjectRef : ExprTypeDef<ObjectRef>("Object_ref") {

    companion object {
        val INSTANCE: TypeDefObjectRef = TypeDefObjectRef()
    }

    override fun toString(): String = "TypeDefObjectRef{} ${super.toString()}"
}