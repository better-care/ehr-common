package org.openehr.rm.datatypes

import care.better.platform.annotation.RequiresNotEmpty

/**
 * @author Primoz Delopst
 */

class DvParagraph : DataValue() {
    @RequiresNotEmpty
    var items: MutableList<DvText> = mutableListOf()
}