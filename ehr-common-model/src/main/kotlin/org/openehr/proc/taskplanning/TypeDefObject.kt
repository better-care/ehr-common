package org.openehr.proc.taskplanning

/**
 * @author Primoz Delopst
 */

class TypeDefObject : ExprTypeDef<Any>("Object") {

    companion object {
        val INSTANCE: TypeDefObject = TypeDefObject()
    }

    override fun toString(): String = "TypeDefObject{} ${super.toString()}"
}