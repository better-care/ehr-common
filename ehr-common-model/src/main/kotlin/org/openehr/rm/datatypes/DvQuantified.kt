package org.openehr.rm.datatypes

import kotlinx.serialization.Serializable

/**
 * @author Primoz Delopst
 */

@Serializable
abstract class DvQuantified : DvOrdered() {
    var magnitudeStatus: String? = null
}