package org.openehr.rm.datatypes

import care.better.platform.annotation.RequiresNotNull

/**
 * @author Primoz Delopst
 */

class DvParsable : DvEncapsulated() {
    @RequiresNotNull
    var value: String? = null

    @RequiresNotNull
    var formalism: String? = null
}