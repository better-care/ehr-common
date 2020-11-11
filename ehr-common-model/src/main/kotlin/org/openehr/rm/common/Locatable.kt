package org.openehr.rm.common

import care.better.openehr.rm.RmObject
import org.openehr.base.basetypes.UidBasedId
import org.openehr.rm.datatypes.DvText
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

abstract class Locatable : RmObject(), Serializable {
    lateinit var name: DvText
    var uid: UidBasedId? = null
    var links: MutableList<Link> = mutableListOf()
    var archetypeDetails: Archetyped? = null
    var feederAudit: FeederAudit? = null
    lateinit var archetypeNodeId: String
}