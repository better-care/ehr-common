package org.openehr.proc.taskplanning

import java.time.LocalDate

/**
 * @author Primoz Delopst
 */


class TypeDefDate : ExprTypeDef<LocalDate>("Date") {

    companion object {
        val INSTANCE: TypeDefDate = TypeDefDate()
    }

    override fun toString(): String = "TypeDefDate{} ${super.toString()}"
}