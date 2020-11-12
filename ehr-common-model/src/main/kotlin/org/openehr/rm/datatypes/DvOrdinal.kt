package org.openehr.rm.datatypes

import care.better.platform.annotation.RequiresNotNull

/**
 * @author Primoz Delopst
 */

class DvOrdinal : DvOrdered() {
    var value = 0

    @RequiresNotNull
    var symbol: DvCodedText? = null
}