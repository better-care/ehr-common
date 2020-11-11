package org.openehr.rm.datatypes

/**
 * @author Primoz Delopst
 */

abstract class DvOrdered : DataValue() {
    var normalRange: DvInterval? = null
    var otherReferenceRanges: MutableList<ReferenceRange> = mutableListOf()
    var normalStatus: CodePhrase? = null
}