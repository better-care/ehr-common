package org.openehr.proc.taskplanning

/**
 * @author Primoz Delopst
 */

class TypeDefReal : ExprTypeDef<Double>("Real") {

    companion object {
        val INSTANCE: TypeDefReal = TypeDefReal()
    }

    override fun setTypeName(typeName: String?) {
        if ("Real" != typeName) {
            throw UnsupportedOperationException("The type name has to be Real")
        }
        super.setTypeName(typeName)
    }

    override fun toString(): String = "TypeDefReal{} ${super.toString()}"
}