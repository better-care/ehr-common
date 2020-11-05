package org.openehr.am.aom

import kotlinx.serialization.Serializable
import org.openehr.rm.datatypes.DvOrdinal

/**
 * @author Primoz Delopst
 */

@Serializable
class CDvOrdinal : CDomainType() {
    var assumedValue: DvOrdinal? = null
    var list: MutableList<DvOrdinal> = mutableListOf()
}