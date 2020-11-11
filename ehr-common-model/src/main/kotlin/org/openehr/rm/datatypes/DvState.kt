package org.openehr.rm.datatypes

/**
 * @author Primoz Delopst
 */

class DvState : DataValue() {
    lateinit var value: DvCodedText
    var isTerminal = false
}