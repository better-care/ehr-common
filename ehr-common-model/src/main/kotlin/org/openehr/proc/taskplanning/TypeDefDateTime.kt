package org.openehr.proc.taskplanning

import java.time.LocalDateTime

/**
 * @author Primoz Delopst
 */

class TypeDefDateTime : ExprTypeDef<LocalDateTime>("Date_time") {

    companion object {
        val INSTANCE: TypeDefDateTime = TypeDefDateTime()
    }

    override fun toString(): String = "TypeDefDateTime{} ${super.toString()}"
}