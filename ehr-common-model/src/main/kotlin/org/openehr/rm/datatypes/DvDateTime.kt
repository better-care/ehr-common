package org.openehr.rm.datatypes

import care.better.platform.annotation.RequiresNotNull

/**
 * @author Primoz Delopst
 */

class DvDateTime : DvTemporal() {
    @RequiresNotNull
    var value: String? = null
}