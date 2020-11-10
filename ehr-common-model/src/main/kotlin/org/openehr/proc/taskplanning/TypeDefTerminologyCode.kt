package org.openehr.proc.taskplanning

/**
 * @author Primoz Delopst
 */

class TypeDefTerminologyCode : ExprTypeDef<String>("Terminology_code") {

    companion object {
        val INSTANCE: TypeDefTerminologyCode = TypeDefTerminologyCode()
    }

    override fun toString(): String = "TypeDefTerminologyCode{} ${super.toString()}"
}