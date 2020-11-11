package org.openehr.am.aom

import care.better.openehr.am.AmObject
import care.better.platform.annotation.RequiresNotEmpty
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

class StateMachine : AmObject(), Serializable {
    @RequiresNotEmpty
    var states: MutableList<State> = mutableListOf()
}