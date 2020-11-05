package org.openehr.am.aom

import kotlinx.serialization.Serializable

/**
 * @author Primoz Delopst
 */

@Serializable
class ExprUnaryOperator : ExprOperator() {
    lateinit var operand: ExprItem
}