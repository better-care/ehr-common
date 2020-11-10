package org.openehr.proc.taskplanning

/**
 * @author Primoz Delopst
 */

class TypeDefString : ExprTypeDef<String>("String") {

    companion object {
        val INSTANCE: TypeDefString = TypeDefString()
    }

    override fun toString(): String = "TypeDefString{} ${super.toString()}"
}