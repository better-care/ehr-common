package org.openehr.rm.composition

import org.openehr.rm.datastructures.History

/**
 * @author Primoz Delopst
 */

class Observation : CareEntry() {
    lateinit var data: History
    var state: History? = null
}