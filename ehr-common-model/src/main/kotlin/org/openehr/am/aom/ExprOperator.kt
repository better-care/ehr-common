package org.openehr.am.aom

import care.better.platform.annotation.RequiresNotNull
import java.math.BigInteger

/**
 * @author Primoz Delopst
 */

abstract class ExprOperator : ExprItem() {
    @RequiresNotNull
    var operator: BigInteger? = null
    var precedenceOverridden: Boolean = false
}