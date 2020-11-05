package org.openehr.am.aom

import kotlinx.serialization.Serializable

/**
 * @author Primoz Delopst
 */

@Serializable
open class CComplexObject : CDefinedObject() {
    var attributes: MutableList<CAttribute> = mutableListOf()
}