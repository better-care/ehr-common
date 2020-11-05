package org.openehr.am.aom

import care.better.openehr.am.AmObject
import kotlinx.serialization.Serializable

/**
 * @author Primoz Delopst
 */

@Serializable
class Transition : AmObject() {
    lateinit var event: String
    var action: String? = null
    var guard: String? = null
    lateinit var nextState: State
}