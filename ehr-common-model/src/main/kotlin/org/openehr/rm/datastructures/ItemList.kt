package org.openehr.rm.datastructures


/**
 * @author Primoz Delopst
 */

class ItemList : ItemStructure() {
    var items: MutableList<Element> = mutableListOf()
}