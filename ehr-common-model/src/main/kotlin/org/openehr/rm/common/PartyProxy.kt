package org.openehr.rm.common

import care.better.openehr.referencemodel.RmObject
import kotlinx.serialization.Serializable
import org.openehr.base.basetypes.PartyRef

/**
 * @author Primoz Delopst
 */

@Serializable
abstract class PartyProxy : RmObject() {
    var externalRef: PartyRef? = null
}