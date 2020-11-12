package org.openehr.rm.datatypes

import care.better.platform.annotation.RequiresNotNull

/**
 * @author Primoz Delopst
 */

class DvDuration : DvAmount() {
    @RequiresNotNull
    var value: String? = null
}