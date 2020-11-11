package org.openehr.rm.datatypes

/**
 * @author Primoz Delopst
 */

class DvQuantity : DvAmount() {
    var magnitude = 0.0
    lateinit var units: String
    var precision: Int = -1
}