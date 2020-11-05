package org.openehr.rm.ehr

import kotlinx.serialization.Serializable
import org.openehr.rm.common.Locatable
import org.openehr.rm.common.PartySelf
import org.openehr.rm.datastructures.ItemStructure

/**
 * @author Primoz Delopst
 */

@Serializable
class EhrStatus : Locatable() {
    lateinit var  subject: PartySelf
    val queryable = true
    val modifiable = true
    val otherDetails: ItemStructure? = null
}