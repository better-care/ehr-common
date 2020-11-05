package org.openehr.am.aom

import care.better.platform.serializers.BigIntegerSerializer
import kotlinx.serialization.Serializable
import org.openehr.base.foundationtypes.IntervalOfDate
import java.math.BigInteger

/**
 * @author Primoz Delopst
 */

@Serializable
class CDate : CPrimitive() {
    var pattern: String? = null
    @Serializable(BigIntegerSerializer::class)
    var timezoneValidity: BigInteger? = null
    var range: IntervalOfDate? = null
    var assumedValue: String? = null
    var defaultValue: String? = null
}