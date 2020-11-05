package org.openehr.rm.datastructures

import kotlinx.serialization.Serializable

/**
 * @author Primoz Delopst
 */

@Serializable
class ItemTable : ItemStructure() {
    var rows: MutableList<Cluster> = mutableListOf()
}