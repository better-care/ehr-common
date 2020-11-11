package org.openehr.am.aom

import java.math.BigInteger

/**
 * @author Primoz Delopst
 */

abstract class ExprOperator : ExprItem() {
    lateinit var operator: BigInteger
    var precedenceOverridden: Boolean = false
}