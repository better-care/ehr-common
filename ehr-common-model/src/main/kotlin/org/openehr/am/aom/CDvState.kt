package org.openehr.am.aom

import kotlinx.serialization.Serializable
import org.openehr.rm.datatypes.DvState

/**
 * @author Primoz Delopst
 */

@Serializable
class CDvState : CDomainType() {
    var assumedValue: DvState? = null
    lateinit var value: StateMachine
}