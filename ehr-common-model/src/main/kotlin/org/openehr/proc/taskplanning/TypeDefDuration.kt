package org.openehr.proc.taskplanning

import java.time.Duration
import java.time.LocalDateTime

/**
 * @author Primoz Delopst
 */

class TypeDefDuration : ExprTypeDef<Duration>("Duration") {

    companion object {
        val INSTANCE: TypeDefDuration = TypeDefDuration()
    }

    override fun toString(): String = "TypeDefDuration{} ${super.toString()}"
}