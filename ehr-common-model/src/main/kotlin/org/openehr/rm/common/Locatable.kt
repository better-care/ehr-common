package org.openehr.rm.common

import care.better.openehr.referencemodel.RmObject
import kotlinx.serialization.Serializable
import org.openehr.base.basetypes.UidBasedId
import org.openehr.rm.datatypes.DvText

/**
 * @author Primoz Delopst
 */

@Serializable
abstract class Locatable : RmObject() {
    lateinit var name: DvText
    var uid: UidBasedId? = null
    var links: MutableList<Link> = mutableListOf()
    var archetypeDetails: Archetyped? = null
    var feederAudit: FeederAudit? = null
    lateinit var archetypeNodeId: String
}