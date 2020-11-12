package org.openehr.proc.taskplanning

/**
 * @author Primoz Delopst
 */

class TypeDefInteger : ExprTypeDef<Int>("Integer") {

    companion object {
        val INSTANCE: TypeDefInteger = TypeDefInteger()
    }

    override fun setTypeName(typeName: String?) {
        if ("Integer" != typeName) {
            throw UnsupportedOperationException("The type name has to be Integer")
        }
        super.setTypeName(typeName)
    }


    override fun toString(): String = "TypeDefInteger{} ${super.toString()}"
}