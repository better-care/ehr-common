package org.openehr.rm.integration

import org.openehr.rm.composition.ContentItem
import org.openehr.rm.datastructures.ItemTree

/**
 * @author Primoz Delopst
 */

class GenericEntry : ContentItem() {
    lateinit var data: ItemTree
}