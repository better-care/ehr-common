package org.openehr.rm.composition

import care.better.platform.annotation.RequiresNotNull
import org.openehr.rm.datastructures.History

/**
 * @author Primoz Delopst
 */

class Observation : CareEntry() {
    @RequiresNotNull
    var data: History? = null
    var state: History? = null
}