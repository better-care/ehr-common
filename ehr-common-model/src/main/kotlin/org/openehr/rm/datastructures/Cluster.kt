package org.openehr.rm.datastructures

import kotlinx.serialization.Serializable

/**
 * @author Primoz Delopst
 */

@Serializable
class Cluster : Item() {
    var items: MutableList<Item> = mutableListOf()
}