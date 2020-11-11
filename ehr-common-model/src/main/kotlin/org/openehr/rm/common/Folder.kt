package org.openehr.rm.common

import org.openehr.base.basetypes.ObjectRef
import org.openehr.rm.datastructures.ItemStructure

/**
 * @author Primoz Delopst
 */

class Folder : Locatable() {
    var folders: MutableList<Folder> = mutableListOf()
    var items: MutableList<ObjectRef> = mutableListOf()
    var details: ItemStructure? = null
}