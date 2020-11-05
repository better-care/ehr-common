package org.openehr.rm.datatypes

import care.better.platform.serializers.BigDecimalSerializer
import kotlinx.serialization.Serializable
import java.math.BigInteger

/**
 * @author Primoz Delopst
 */

@Serializable
class DvProportion : DvAmount() {
    var numerator = 0f
    var denominator = 0f
    @Serializable(BigDecimalSerializer::class)
    lateinit var type: BigInteger
    var precision: Int = -1
}