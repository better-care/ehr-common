package org.openehr.rm.datatypes

import care.better.platform.annotation.RequiresNotNull

/**
 * @author Primoz Delopst
 */

class DvDate : DvTemporal() {
    @RequiresNotNull
    var value: String? = null
}