package org.openehr.proc.taskplanning

/**
 * @author Primoz Delopst
 */
interface ExpressionNamesProvider {
    fun getExpressionNames(): Sequence<String>
}