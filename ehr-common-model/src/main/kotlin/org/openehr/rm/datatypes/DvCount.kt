package org.openehr.rm.datatypes

import kotlinx.serialization.Serializable

/**
 * @author Primoz Delopst
 */

@Serializable
class DvCount : DvAmount(){
    var magnitude: Long = 0L
}