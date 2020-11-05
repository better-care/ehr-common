package org.openehr.am.aom

import kotlinx.serialization.Serializable
import org.openehr.rm.datatypes.DataValue

/**
 * @author Primoz Delopst
 */

@Serializable
class TComplexObject : CComplexObject() {
    var defaultValue: DataValue? = null
}