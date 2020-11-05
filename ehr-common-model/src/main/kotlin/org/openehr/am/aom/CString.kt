package org.openehr.am.aom

import kotlinx.serialization.Serializable

/**
 * @author Primoz Delopst
 */

@Serializable
class CString : CPrimitive() {
    var pattern: String? = null
    var list: MutableList<String> = mutableListOf()
    var listOpen: Boolean? = null
    var assumedValue: String? = null
    var defaultValue: String? = null
}