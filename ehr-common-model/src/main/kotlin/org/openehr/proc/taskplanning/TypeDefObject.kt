package org.openehr.proc.taskplanning

/**
 * @author Primoz Delopst
 */

class TypeDefObject : ExprTypeDef<Any>("Object") {

    companion object {
        val INSTANCE: TypeDefObject = TypeDefObject()
    }

    override fun setTypeName(typeName: String?) {
        if ("Object" != typeName) {
            throw UnsupportedOperationException("The type name has to be Object")
        }
        super.setTypeName(typeName)
    }

    override fun toString(): String = "TypeDefObject{} ${super.toString()}"
}