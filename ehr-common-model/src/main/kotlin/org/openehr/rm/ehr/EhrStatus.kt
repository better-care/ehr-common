package org.openehr.rm.ehr

import care.better.platform.annotation.OpenEhrName
import care.better.platform.annotation.RequiresNotNull
import org.openehr.rm.common.Locatable
import org.openehr.rm.common.PartySelf
import org.openehr.rm.datastructures.ItemStructure

/**
 * @author Primoz Delopst
 */

class EhrStatus : Locatable() {
    @RequiresNotNull
    var subject: PartySelf? = null

    @OpenEhrName("is_queryable")
    val queryable = true

    @OpenEhrName("is_modifiable")
    val modifiable = true
    val otherDetails: ItemStructure? = null
}