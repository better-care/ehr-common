package org.openehr.am.aom

import care.better.openehr.am.AmObject
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

class StateMachine : AmObject(), Serializable {
    var states: MutableList<State> = mutableListOf()
}