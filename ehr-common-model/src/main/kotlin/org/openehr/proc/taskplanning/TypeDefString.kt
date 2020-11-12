package org.openehr.proc.taskplanning

/**
 * @author Primoz Delopst
 */

class TypeDefString : ExprTypeDef<String>("String") {

    companion object {
        val INSTANCE: TypeDefString = TypeDefString()
    }

    override fun setTypeName(typeName: String?) {
        if ("String" != typeName) {
            throw UnsupportedOperationException("The type name has to be String")
        }
        super.setTypeName(typeName)
    }

    override fun toString(): String = "TypeDefString{} ${super.toString()}"
}