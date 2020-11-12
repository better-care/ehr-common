package org.openehr.rm.composition

import care.better.platform.annotation.RequiresNotNull
import org.openehr.rm.datastructures.ItemStructure

/**
 * @author Primoz Delopst
 */

class AdminEntry : Entry() {
    @RequiresNotNull
    var data: ItemStructure? = null
}