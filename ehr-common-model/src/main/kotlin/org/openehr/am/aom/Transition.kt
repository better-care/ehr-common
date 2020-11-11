package org.openehr.am.aom

import care.better.openehr.am.AmObject
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

class Transition : AmObject(), Serializable {
    lateinit var event: String
    var action: String? = null
    var guard: String? = null
    lateinit var nextState: State
}