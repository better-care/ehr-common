package org.openehr.rm.datastructures


/**
 * @author Primoz Delopst
 */

class ItemTable : ItemStructure() {
    var rows: MutableList<Cluster> = mutableListOf()
}