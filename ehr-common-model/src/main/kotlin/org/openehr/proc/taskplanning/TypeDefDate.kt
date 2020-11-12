package org.openehr.proc.taskplanning

import java.time.LocalDate

/**
 * @author Primoz Delopst
 */


class TypeDefDate : ExprTypeDef<LocalDate>("Date") {

    companion object {
        val INSTANCE: TypeDefDate = TypeDefDate()
    }

    override fun setTypeName(typeName: String?) {
        if ("Date" != typeName) {
            throw UnsupportedOperationException("The type name has to be Date")
        }
        super.setTypeName(typeName)
    }

    override fun toString(): String = "TypeDefDate{} ${super.toString()}"
}