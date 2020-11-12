package org.openehr.proc.taskplanning

import org.openehr.base.basetypes.ObjectRef

/**
 * @author Primoz Delopst
 */

class TypeDefObjectRef : ExprTypeDef<ObjectRef>("Object_ref") {

    companion object {
        val INSTANCE: TypeDefObjectRef = TypeDefObjectRef()
    }

    override fun setTypeName(typeName: String?) {
        if ("Object_ref" != typeName) {
            throw UnsupportedOperationException("The type name has to be Object_ref")
        }
        super.setTypeName(typeName)
    }

    override fun toString(): String = "TypeDefObjectRef{} ${super.toString()}"
}