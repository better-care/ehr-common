package org.openehr.rm.composition

import kotlinx.serialization.Serializable
import org.openehr.base.basetypes.ObjectRef
import org.openehr.rm.common.Participation
import org.openehr.rm.common.PartyProxy
import org.openehr.rm.datatypes.CodePhrase

/**
 * @author Primoz Delopst
 */

@Serializable
abstract class Entry : ContentItem() {
    lateinit var language: CodePhrase
    lateinit var encoding: CodePhrase
    lateinit var subject: PartyProxy
    var provider: PartyProxy? = null
    var otherParticipations: MutableList<Participation> = mutableListOf()
    var workFlowId: ObjectRef? = null
}