package org.openehr.rm.datatypes

/**
 * @author Primoz Delopst
 */

class DvOrdinal : DvOrdered() {
    var value = 0
    lateinit var symbol: DvCodedText
}