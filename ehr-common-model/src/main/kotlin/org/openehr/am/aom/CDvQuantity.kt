package org.openehr.am.aom

import org.openehr.rm.datatypes.CodePhrase
import org.openehr.rm.datatypes.DvQuantity

/**
 * @author Primoz Delopst
 */

class CDvQuantity : CDomainType() {
    var assumedValue: DvQuantity? = null
    var defaultValue: DvQuantity? = null
    var property: CodePhrase? = null
    var list: MutableList<CQuantityItem> = mutableListOf()
}