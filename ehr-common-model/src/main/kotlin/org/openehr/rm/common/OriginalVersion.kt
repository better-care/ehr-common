package org.openehr.rm.common

import org.openehr.base.basetypes.ObjectVersionId
import org.openehr.rm.datatypes.DvCodedText

/**
 * @author Primoz Delopst
 */

class OriginalVersion : Version() {
    lateinit var uid: ObjectVersionId
    var data: Any? = null
    var precedingVersionUid: ObjectVersionId? = null
    var otherInputVersionUids: MutableList<ObjectVersionId> = mutableListOf()
    var attestations: MutableList<Attestation> = mutableListOf()
    lateinit var lifecycleState: DvCodedText
}