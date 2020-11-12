package org.openehr.proc.taskplanning

/**
 * @author Primoz Delopst
 */

class TypeDefBoolean : ExprTypeDef<Boolean>("Boolean") {

    companion object {
        val INSTANCE: TypeDefBoolean = TypeDefBoolean()
    }

    override fun setTypeName(typeName: String?) {
        if ("Boolean" != typeName) {
            throw UnsupportedOperationException("The type name has to be Boolean")
        }
        super.setTypeName(typeName)
    }


    override fun toString(): String = "TypeDefBoolean{} ${super.toString()}"
}