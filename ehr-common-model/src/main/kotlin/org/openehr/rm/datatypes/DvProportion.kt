package org.openehr.rm.datatypes

import care.better.platform.annotation.RequiresNotNull
import java.math.BigInteger

/**
 * @author Primoz Delopst
 */

class DvProportion : DvAmount() {
    var numerator = 0f
    var denominator = 0f

    @RequiresNotNull
    var type: BigInteger? = null
    var precision: Int = -1
}