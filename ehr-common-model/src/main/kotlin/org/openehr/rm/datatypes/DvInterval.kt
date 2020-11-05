package org.openehr.rm.datatypes

import kotlinx.serialization.Serializable

/**
 * @author Primoz Delopst
 */

@Serializable
class DvInterval : DataValue() {
    var lower: DvOrdered? = null
    var upper: DvOrdered? = null
    var lowerIncluded: Boolean? = null
    var upperIncluded: Boolean? = null
    var lowerUnbounded = false
    var upperUnbounded = false
}