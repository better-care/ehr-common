package org.openehr.rm.datastructures

/**
 * @author Primoz Delopst
 */

class Cluster : Item() {
    var items: MutableList<Item> = mutableListOf()
}