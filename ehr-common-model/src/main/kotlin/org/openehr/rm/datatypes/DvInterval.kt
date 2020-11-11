package org.openehr.rm.datatypes

/**
 * @author Primoz Delopst
 */

class DvInterval : DataValue() {
    var lower: DvOrdered? = null
    var upper: DvOrdered? = null
    var lowerIncluded: Boolean? = null
    var upperIncluded: Boolean? = null
    var lowerUnbounded = false
    var upperUnbounded = false
}