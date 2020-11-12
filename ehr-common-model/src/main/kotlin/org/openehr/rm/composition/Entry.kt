package org.openehr.rm.composition

import care.better.platform.annotation.RequiresNotNull
import org.openehr.base.basetypes.ObjectRef
import org.openehr.rm.common.Participation
import org.openehr.rm.common.PartyProxy
import org.openehr.rm.datatypes.CodePhrase

/**
 * @author Primoz Delopst
 */

abstract class Entry : ContentItem() {
    @RequiresNotNull
    var language: CodePhrase? = null

    @RequiresNotNull
    var encoding: CodePhrase? = null

    @RequiresNotNull
    var subject: PartyProxy? = null
    var provider: PartyProxy? = null
    var otherParticipations: MutableList<Participation> = mutableListOf()
    var workFlowId: ObjectRef? = null
}