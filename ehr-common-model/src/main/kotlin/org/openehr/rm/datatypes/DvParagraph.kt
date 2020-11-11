package org.openehr.rm.datatypes

/**
 * @author Primoz Delopst
 */

class DvParagraph : DataValue() {
    var items: MutableList<DvText> = mutableListOf()
}