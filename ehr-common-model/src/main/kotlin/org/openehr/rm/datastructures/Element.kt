package org.openehr.rm.datastructures

import org.openehr.rm.datatypes.DataValue
import org.openehr.rm.datatypes.DvCodedText

/**
 * @author Primoz Delopst
 */

class Element : Item() {
    var value: DataValue? = null
    var nullFlavour: DvCodedText? = null
}