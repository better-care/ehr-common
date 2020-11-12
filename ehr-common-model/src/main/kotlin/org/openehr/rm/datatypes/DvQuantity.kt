package org.openehr.rm.datatypes

import care.better.platform.annotation.RequiresNotNull

/**
 * @author Primoz Delopst
 */

class DvQuantity : DvAmount() {
    var magnitude = 0.0

    @RequiresNotNull
    var units: String? = null
    var precision: Int = -1
}