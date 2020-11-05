package org.openehr.rm.datatypes

import kotlinx.serialization.Serializable

/**
 * @author Primoz Delopst
 */

@Serializable
class DvBoolean : DataValue() {
    var value: Boolean = false
}