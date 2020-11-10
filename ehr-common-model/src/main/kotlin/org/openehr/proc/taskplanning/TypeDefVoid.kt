package org.openehr.proc.taskplanning

/**
 * @author Primoz Delopst
 */

class TypeDefVoid : ExprTypeDef<Void>("Void") {

    companion object {
        val INSTANCE: TypeDefVoid = TypeDefVoid()
    }

    override fun toString(): String = "TypeDefVoid{} ${super.toString()}"
}