package org.openehr.rm.datatypes

import kotlinx.serialization.Serializable

/**
 * @author Primoz Delopst
 */

@Serializable
class DvState : DataValue() {
    lateinit var value: DvCodedText
    var isTerminal = false
}