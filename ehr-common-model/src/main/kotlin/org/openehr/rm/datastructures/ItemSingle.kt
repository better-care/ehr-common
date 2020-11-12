package org.openehr.rm.datastructures

import care.better.platform.annotation.RequiresNotNull


/**
 * @author Primoz Delopst
 */

class ItemSingle : ItemStructure() {
    @RequiresNotNull
    var item: Element? = null
}