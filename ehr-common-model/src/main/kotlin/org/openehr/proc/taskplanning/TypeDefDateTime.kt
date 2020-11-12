package org.openehr.proc.taskplanning

import java.time.LocalDateTime

/**
 * @author Primoz Delopst
 */

class TypeDefDateTime : ExprTypeDef<LocalDateTime>("Date_time") {

    companion object {
        val INSTANCE: TypeDefDateTime = TypeDefDateTime()
    }

    override fun setTypeName(typeName: String?) {
        if ("Date_time" != typeName) {
            throw UnsupportedOperationException("The type name has to be Date_time")
        }
        super.setTypeName(typeName)
    }

    override fun toString(): String = "TypeDefDateTime{} ${super.toString()}"
}