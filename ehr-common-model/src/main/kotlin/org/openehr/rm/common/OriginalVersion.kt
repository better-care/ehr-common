package org.openehr.rm.common

import care.better.platform.annotation.RequiresNotNull
import org.openehr.base.basetypes.ObjectVersionId
import org.openehr.rm.datatypes.DvCodedText

/**
 * @author Primoz Delopst
 */

class OriginalVersion : Version() {
    @RequiresNotNull
    var uid: ObjectVersionId? = null
    var data: Any? = null
    var precedingVersionUid: ObjectVersionId? = null
    var otherInputVersionUids: MutableList<ObjectVersionId> = mutableListOf()
    var attestations: MutableList<Attestation> = mutableListOf()

    @RequiresNotNull
    var lifecycleState: DvCodedText? = null
}