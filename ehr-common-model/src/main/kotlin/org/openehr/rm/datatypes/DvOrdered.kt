package org.openehr.rm.datatypes

import kotlinx.serialization.Serializable

/**
 * @author Primoz Delopst
 */

@Serializable
abstract class DvOrdered : DataValue() {
    var normalRange: DvInterval? = null
    var otherReferenceRanges: MutableList<ReferenceRange> = mutableListOf()
    var normalStatus: CodePhrase? = null
}