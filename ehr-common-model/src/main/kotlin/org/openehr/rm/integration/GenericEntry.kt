package org.openehr.rm.integration

import kotlinx.serialization.Serializable
import org.openehr.rm.composition.ContentItem
import org.openehr.rm.datastructures.ItemTree

/**
 * @author Primoz Delopst
 */

@Serializable
class GenericEntry : ContentItem() {
    lateinit var data: ItemTree
}