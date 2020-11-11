package org.openehr.am.aom

import org.openehr.rm.datatypes.DvState

/**
 * @author Primoz Delopst
 */

class CDvState : CDomainType() {
    var assumedValue: DvState? = null
    lateinit var value: StateMachine
}