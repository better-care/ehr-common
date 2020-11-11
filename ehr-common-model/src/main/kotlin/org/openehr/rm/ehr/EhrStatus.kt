package org.openehr.rm.ehr

import care.better.platform.annotation.OpenEhrName
import org.openehr.rm.common.Locatable
import org.openehr.rm.common.PartySelf
import org.openehr.rm.datastructures.ItemStructure

/**
 * @author Primoz Delopst
 */

class EhrStatus : Locatable() {
    lateinit var  subject: PartySelf
    @OpenEhrName("is_queryable")
    val queryable = true
    @OpenEhrName("is_modifiable")
    val modifiable = true
    val otherDetails: ItemStructure? = null
}