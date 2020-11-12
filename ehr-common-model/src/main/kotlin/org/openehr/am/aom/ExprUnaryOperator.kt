package org.openehr.am.aom

import care.better.platform.annotation.RequiresNotNull

/**
 * @author Primoz Delopst
 */

class ExprUnaryOperator : ExprOperator() {
    @RequiresNotNull
    var operand: ExprItem? = null
}