package org.openehr.am.aom


/**
 * @author Primoz Delopst
 */

open class CComplexObject : CDefinedObject() {
    var attributes: MutableList<CAttribute> = mutableListOf()
}