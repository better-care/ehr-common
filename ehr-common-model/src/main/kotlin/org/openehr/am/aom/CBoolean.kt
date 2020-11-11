package org.openehr.am.aom

/**
 * @author Primoz Delopst
 */

class CBoolean : CPrimitive() {
    var trueValid = false
    var falseValid = false
    var assumedValue: Boolean? = null
    var defaultValue: Boolean? = null
}