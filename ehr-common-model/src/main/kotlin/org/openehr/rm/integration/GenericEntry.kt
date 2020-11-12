package org.openehr.rm.integration

import care.better.platform.annotation.RequiresNotNull
import org.openehr.rm.composition.ContentItem
import org.openehr.rm.datastructures.ItemTree

/**
 * @author Primoz Delopst
 */

class GenericEntry : ContentItem() {
    @RequiresNotNull
    var data: ItemTree? = null
}