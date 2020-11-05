package org.openehr.rm.common

import kotlinx.serialization.Serializable
import org.openehr.base.basetypes.ObjectRef
import org.openehr.rm.datastructures.ItemStructure

/**
 * @author Primoz Delopst
 */

@Serializable
class Folder : Locatable(){
    var folders: MutableList<Folder> = mutableListOf()
    var items: MutableList<ObjectRef> = mutableListOf()
    var details: ItemStructure? = null
}