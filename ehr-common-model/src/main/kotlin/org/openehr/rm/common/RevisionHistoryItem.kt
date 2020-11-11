package org.openehr.rm.common

import care.better.openehr.rm.RmObject
import org.openehr.base.basetypes.ObjectVersionId
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

class RevisionHistoryItem : RmObject(), Serializable {
    lateinit var versionId: ObjectVersionId
    var audits: MutableList<AuditDetails> = mutableListOf()
}