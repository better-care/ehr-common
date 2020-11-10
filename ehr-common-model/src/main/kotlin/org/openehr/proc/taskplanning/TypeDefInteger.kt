package org.openehr.proc.taskplanning

/**
 * @author Primoz Delopst
 */

class TypeDefInteger : ExprTypeDef<Int>("Integer") {

    companion object {
        val INSTANCE: TypeDefInteger = TypeDefInteger()
    }

    override fun toString(): String = "TypeDefInteger{} ${super.toString()}"
}