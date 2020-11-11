package org.openehr.rm.composition

/**
 * @author Primoz Delopst
 */

class Section : ContentItem() {
    var items: MutableList<ContentItem> = mutableListOf()
}