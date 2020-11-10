package org.openehr.proc.taskplanning

import java.math.BigDecimal

/**
 * @author Primoz Delopst
 */

class ContinuousEventVariable<T> : EventVariable<T> {
    var updateVariation: BigDecimal? = null
}