package org.openehr.am.aom

/**
 * @author Primoz Delopst
 */

class NonTerminalState : State() {
    var transitions: MutableList<Transition> = mutableListOf()
}