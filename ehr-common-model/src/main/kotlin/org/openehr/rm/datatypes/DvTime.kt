package org.openehr.rm.datatypes

import care.better.platform.annotation.RequiresNotNull

/**
 * @author Primoz Delopst
 */

class DvTime : DvTemporal() {
    @RequiresNotNull
    var value: String? = null
}