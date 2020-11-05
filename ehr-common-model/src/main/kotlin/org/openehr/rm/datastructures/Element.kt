package org.openehr.rm.datastructures

import kotlinx.serialization.Serializable
import org.openehr.rm.datatypes.DataValue
import org.openehr.rm.datatypes.DvCodedText

/**
 * @author Primoz Delopst
 */

@Serializable
class Element : Item() {
    var value: DataValue? = null
    var nullFlavour: DvCodedText? = null
}