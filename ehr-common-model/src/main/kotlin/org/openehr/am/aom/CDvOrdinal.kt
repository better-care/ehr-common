package org.openehr.am.aom

import org.openehr.rm.datatypes.DvOrdinal

/**
 * @author Primoz Delopst
 */

class CDvOrdinal : CDomainType() {
    var assumedValue: DvOrdinal? = null
    var list: MutableList<DvOrdinal> = mutableListOf()
}