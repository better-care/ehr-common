package org.openehr.am.aom

import kotlinx.serialization.Serializable

/**
 * @author Primoz Delopst
 */

@Serializable
class ExprBinaryOperator : ExprOperator() {
    lateinit var leftOperand: ExprItem
    lateinit var rightOperand: ExprItem
}