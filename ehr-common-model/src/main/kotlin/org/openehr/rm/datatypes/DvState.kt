package org.openehr.rm.datatypes

import care.better.platform.annotation.RequiresNotNull

/**
 * @author Primoz Delopst
 */

class DvState : DataValue() {
    @RequiresNotNull
    var value: DvCodedText? = null
    var isTerminal = false
}