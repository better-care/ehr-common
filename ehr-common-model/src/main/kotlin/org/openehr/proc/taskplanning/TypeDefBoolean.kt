package org.openehr.proc.taskplanning

/**
 * @author Primoz Delopst
 */

class TypeDefBoolean : ExprTypeDef<Boolean>("Boolean") {

    companion object {
        val INSTANCE: TypeDefBoolean = TypeDefBoolean()
    }

    override fun toString(): String = "TypeDefBoolean{} ${super.toString()}"
}