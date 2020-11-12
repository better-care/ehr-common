package org.openehr.proc.taskplanning

/**
 * @author Primoz Delopst
 */

class TypeDefTerminologyCode : ExprTypeDef<String>("Terminology_code") {

    companion object {
        val INSTANCE: TypeDefTerminologyCode = TypeDefTerminologyCode()
    }

    override fun setTypeName(typeName: String?) {
        if ("Terminology_code" != typeName) {
            throw UnsupportedOperationException("The type name has to be Terminology_code")
        }
        super.setTypeName(typeName)
    }

    override fun toString(): String = "TypeDefTerminologyCode{} ${super.toString()}"
}