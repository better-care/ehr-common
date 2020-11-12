package org.openehr.am.aom

import care.better.platform.annotation.RequiresNotNull

/**
 * @author Primoz Delopst
 */

class ExprBinaryOperator : ExprOperator() {
    @RequiresNotNull
    var leftOperand: ExprItem? = null
    @RequiresNotNull
    var rightOperand: ExprItem? = null
}