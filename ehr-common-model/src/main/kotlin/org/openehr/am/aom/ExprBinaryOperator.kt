package org.openehr.am.aom

/**
 * @author Primoz Delopst
 */

class ExprBinaryOperator : ExprOperator() {
    lateinit var leftOperand: ExprItem
    lateinit var rightOperand: ExprItem
}