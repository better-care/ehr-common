package org.openehr.rm.common

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.openehr.base.basetypes.ObjectVersionId
import org.openehr.rm.datatypes.DvCodedText

/**
 * @author Primoz Delopst
 */

@Serializable
class OriginalVersion : Version() {
    lateinit var uid: ObjectVersionId
    @Contextual
    var data: Any? = null
    var precedingVersionUid: ObjectVersionId? = null
    var otherInputVersionUids: MutableList<ObjectVersionId> = mutableListOf()
    var attestations: MutableList<Attestation> = mutableListOf()
    lateinit var lifecycleState: DvCodedText
}