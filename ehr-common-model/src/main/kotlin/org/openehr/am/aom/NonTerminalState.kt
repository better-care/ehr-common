package org.openehr.am.aom

import kotlinx.serialization.Serializable

/**
 * @author Primoz Delopst
 */

@Serializable
class NonTerminalState : State() {
    var transitions: MutableList<Transition> = mutableListOf()
}