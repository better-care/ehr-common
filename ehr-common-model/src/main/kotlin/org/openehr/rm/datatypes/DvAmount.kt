package org.openehr.rm.datatypes

import kotlinx.serialization.Serializable

/**
 * @author Primoz Delopst
 */

@Serializable
open class DvAmount : DvQuantified() {
    var accuracy: Float? = -1.0f
    var accuracyIsPercent: Boolean? = null
}