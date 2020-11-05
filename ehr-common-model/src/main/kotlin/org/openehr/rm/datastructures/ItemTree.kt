package org.openehr.rm.datastructures

import kotlinx.serialization.Serializable

/**
 * @author Primoz Delopst
 */

@Serializable
class ItemTree : ItemStructure() {
    var items: MutableList<Item> = mutableListOf()
}