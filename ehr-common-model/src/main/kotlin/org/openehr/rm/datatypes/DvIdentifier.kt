package org.openehr.rm.datatypes

import care.better.platform.annotation.RequiresNotNull

/**
 * @author Primoz Delopst
 */

class DvIdentifier : DataValue() {
    var issuer: String? = null
    var assigner: String? = null

    @RequiresNotNull
    var id: String? = null
    var type: String? = null
}