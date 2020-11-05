package org.openehr.rm.composition

import kotlinx.serialization.Serializable
import org.openehr.rm.datastructures.History

/**
 * @author Primoz Delopst
 */

@Serializable
class Observation : CareEntry() {
    lateinit var data: History
    var state: History? = null
}