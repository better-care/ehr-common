package org.openehr.rm.common

import care.better.openehr.rm.RmObject
import org.openehr.base.basetypes.PartyRef
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

abstract class PartyProxy : RmObject(), Serializable {
    var externalRef: PartyRef? = null
}