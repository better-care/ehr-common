package org.openehr.proc.taskplanning

/**
 * @author Primoz Delopst
 */

class TypeDefVoid : ExprTypeDef<Void>("Void") {

    companion object {
        val INSTANCE: TypeDefVoid = TypeDefVoid()
    }

    override fun setTypeName(typeName: String?) {
        if ("Void" != typeName) {
            throw UnsupportedOperationException("The type name has to be Void")
        }
        super.setTypeName(typeName)
    }

    override fun toString(): String = "TypeDefVoid{} ${super.toString()}"
}