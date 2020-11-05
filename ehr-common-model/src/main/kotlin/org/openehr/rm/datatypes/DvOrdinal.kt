package org.openehr.rm.datatypes

import kotlinx.serialization.Serializable

/**
 * @author Primoz Delopst
 */

@Serializable
class DvOrdinal : DvOrdered() {
    var value = 0
    lateinit var symbol: DvCodedText
}