package org.openehr.proc.taskplanning

import java.time.Duration

/**
 * @author Primoz Delopst
 */

class TypeDefDuration : ExprTypeDef<Duration>("Duration") {

    companion object {
        val INSTANCE: TypeDefDuration = TypeDefDuration()
    }

    override fun setTypeName(typeName: String?) {
        if ("Duration" != typeName) {
            throw UnsupportedOperationException("The type name has to be Duration")
        }
        super.setTypeName(typeName)
    }

    override fun toString(): String = "TypeDefDuration{} ${super.toString()}"
}