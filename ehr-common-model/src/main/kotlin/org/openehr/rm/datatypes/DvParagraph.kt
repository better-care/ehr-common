package org.openehr.rm.datatypes

import kotlinx.serialization.Serializable

/**
 * @author Primoz Delopst
 */

@Serializable
class DvParagraph : DataValue() {
    var items: MutableList<DvText> = mutableListOf()
}