package org.openehr.rm.datatypes

import java.math.BigInteger

/**
 * @author Primoz Delopst
 */

class DvProportion : DvAmount() {
    var numerator = 0f
    var denominator = 0f
    lateinit var type: BigInteger
    var precision: Int = -1
}