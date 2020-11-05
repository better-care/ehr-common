package org.openehr.am.aom

import care.better.platform.serializers.BigIntegerSerializer
import kotlinx.serialization.Serializable
import org.openehr.base.foundationtypes.IntervalOfDateTime
import java.math.BigInteger

/**
 * @author Primoz Delopst
 */

@Serializable
class CDateTime : CPrimitive() {
    var pattern: String? = null
    @Serializable(BigIntegerSerializer::class)
    var timezoneValidity: BigInteger? = null
    var range: IntervalOfDateTime? = null
    var assumedValue: String? = null
    var defaultValue: String? = null
}