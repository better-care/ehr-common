package org.openehr.am.aom

import kotlinx.serialization.Serializable

/**
 * @author Primoz Delopst
 */

@Serializable
class CBoolean : CPrimitive() {
    var trueValid = false
    var falseValid = false
    var assumedValue: Boolean? = null
    var defaultValue: Boolean? = null
}