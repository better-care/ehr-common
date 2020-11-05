package org.openehr.rm.datatypes

import kotlinx.serialization.Serializable

/**
 * @author Primoz Delopst
 */

@Serializable
class DvQuantity : DvAmount() {
    var magnitude = 0.0
    lateinit var units: String
    var precision: Int = -1
}