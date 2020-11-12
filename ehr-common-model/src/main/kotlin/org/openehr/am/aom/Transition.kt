package org.openehr.am.aom

import care.better.openehr.am.AmObject
import care.better.platform.annotation.RequiresNotNull
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

class Transition : AmObject(), Serializable {
    @RequiresNotNull
    var event: String? = null
    var action: String? = null
    var guard: String? = null

    @RequiresNotNull
    var nextState: State? = null
}