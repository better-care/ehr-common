package org.openehr.rm.datatypes

import kotlinx.serialization.Serializable

/**
 * @author Primoz Delopst
 */

@Serializable
open class DvTemporal : DvQuantified() {
    var accuracy: DvDuration? = null
}