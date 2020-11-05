package org.openehr.am.aom

import care.better.platform.serializers.BigIntegerSerializer
import kotlinx.serialization.Serializable
import java.math.BigInteger

/**
 * @author Primoz Delopst
 */

@Serializable
abstract class ExprOperator : ExprItem() {
    @Serializable(BigIntegerSerializer::class)
    lateinit var operator: BigInteger
    var precedenceOverridden: Boolean = false
}