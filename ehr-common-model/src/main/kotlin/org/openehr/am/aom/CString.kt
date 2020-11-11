package org.openehr.am.aom

/**
 * @author Primoz Delopst
 */

class CString : CPrimitive() {
    var pattern: String? = null
    var list: MutableList<String> = mutableListOf()
    var listOpen: Boolean? = null
    var assumedValue: String? = null
    var defaultValue: String? = null
}