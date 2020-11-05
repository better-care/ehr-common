package org.openehr.am.aom

import care.better.platform.serializers.BigIntegerSerializer
import kotlinx.serialization.Serializable
import org.openehr.base.foundationtypes.IntervalOfTime
import java.math.BigInteger

/**
 * @author Primoz Delopst
 */

@Serializable
class CTime : CPrimitive() {
    var pattern: String? = null
    @Serializable(BigIntegerSerializer::class)
    var timezoneValidity: BigInteger? = null
    var range: IntervalOfTime? = null
    var assumedValue: String? = null
    var defaultValue: String? = null
}