package org.openehr.am.aom

import kotlinx.serialization.Serializable
import org.openehr.rm.datatypes.CodePhrase
import org.openehr.rm.datatypes.DvQuantity

/**
 * @author Primoz Delopst
 */


@Serializable
class CDvQuantity : CDomainType(){
    var assumedValue: DvQuantity? = null
    var defaultValue: DvQuantity? = null
    var property: CodePhrase? = null
    var list: MutableList<CQuantityItem> = mutableListOf()
}