package org.openehr.rm.datastructures

import care.better.platform.annotation.RequiresNotEmpty

/**
 * @author Primoz Delopst
 */

class Cluster : Item() {
    @RequiresNotEmpty
    var items: MutableList<Item> = mutableListOf()
}