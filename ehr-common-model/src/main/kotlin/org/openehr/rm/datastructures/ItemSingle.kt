package org.openehr.rm.datastructures

import kotlinx.serialization.Serializable

/**
 * @author Primoz Delopst
 */

@Serializable
class ItemSingle : ItemStructure() {
    lateinit var item: Element
}