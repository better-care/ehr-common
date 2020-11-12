package org.openehr.rm.datatypes

import care.better.platform.annotation.RequiresNotNull

/**
 * @author Primoz Delopst
 */

abstract class DvTimeSpecification : DataValue() {
    @RequiresNotNull
    var value: DvParsable? = null
}