package org.openehr.am.aom

import care.better.platform.annotation.RequiresNotNull
import org.openehr.rm.datatypes.DvState

/**
 * @author Primoz Delopst
 */

class CDvState : CDomainType() {
    var assumedValue: DvState? = null

    @RequiresNotNull
    var value: StateMachine? = null
}