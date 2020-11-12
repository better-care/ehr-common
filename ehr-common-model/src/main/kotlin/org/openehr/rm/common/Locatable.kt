package org.openehr.rm.common

import care.better.openehr.rm.RmObject
import care.better.platform.annotation.RequiresNotNull
import org.openehr.base.basetypes.UidBasedId
import org.openehr.rm.datatypes.DvText
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

abstract class Locatable : RmObject(), Serializable {
    @RequiresNotNull
    var name: DvText? = null
    var uid: UidBasedId? = null
    var links: MutableList<Link> = mutableListOf()
    var archetypeDetails: Archetyped? = null
    var feederAudit: FeederAudit? = null

    @RequiresNotNull
    var archetypeNodeId: String? = null
}