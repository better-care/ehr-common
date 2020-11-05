package org.openehr.am.aom

import kotlinx.serialization.Serializable

/**
 * @author Primoz Delopst
 */

@Serializable
class CPrimitiveObject : CDefinedObject() {
    var item: CPrimitive? = null
}