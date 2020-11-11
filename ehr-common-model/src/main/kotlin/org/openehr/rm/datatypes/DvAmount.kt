package org.openehr.rm.datatypes

/**
 * @author Primoz Delopst
 */

open class DvAmount : DvQuantified() {
    var accuracy: Float? = -1.0f
    var accuracyIsPercent: Boolean? = null
}