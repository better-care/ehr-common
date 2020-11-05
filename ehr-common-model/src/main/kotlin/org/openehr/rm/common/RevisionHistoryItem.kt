package org.openehr.rm.common

import care.better.openehr.referencemodel.RmObject
import kotlinx.serialization.Serializable
import org.openehr.base.basetypes.ObjectVersionId

/**
 * @author Primoz Delopst
 */

@Serializable
class RevisionHistoryItem : RmObject() {
    lateinit var versionId: ObjectVersionId
    var audits: MutableList<AuditDetails> = mutableListOf()
}