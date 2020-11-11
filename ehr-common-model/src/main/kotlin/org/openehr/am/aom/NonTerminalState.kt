package org.openehr.am.aom

import care.better.platform.annotation.RequiresNotEmpty

/**
 * @author Primoz Delopst
 */

class NonTerminalState : State() {
    @RequiresNotEmpty
    var transitions: MutableList<Transition> = mutableListOf()
}