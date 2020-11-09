package org.openehr.rm.ehr

import kotlinx.serialization.SerialName
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
    @SerialName("is_queryable")
    val queryable = true
    @SerialName("is_modifiable")
    val modifiable = true
    val otherDetails: ItemStructure? = null
}