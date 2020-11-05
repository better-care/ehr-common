package org.openehr.rm.datastructures

import kotlinx.serialization.Serializable

/**
 * @author Primoz Delopst
 */

@Serializable
class ItemList : ItemStructure() {
    var items: MutableList<Element> = mutableListOf()
}