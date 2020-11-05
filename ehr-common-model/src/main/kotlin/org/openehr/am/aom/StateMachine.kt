package org.openehr.am.aom

import care.better.openehr.archetypemodel.AmObject
import kotlinx.serialization.Serializable

/**
 * @author Primoz Delopst
 */

@Serializable
class StateMachine : AmObject() {
    var states: MutableList<State> = mutableListOf()
}