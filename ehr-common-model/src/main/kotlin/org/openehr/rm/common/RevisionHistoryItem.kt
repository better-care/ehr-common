package org.openehr.rm.common

import care.better.openehr.rm.RmObject
import care.better.platform.annotation.RequiresNotEmpty
import care.better.platform.annotation.RequiresNotNull
import org.openehr.base.basetypes.ObjectVersionId
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

class RevisionHistoryItem : RmObject(), Serializable {
    @RequiresNotNull
    var versionId: ObjectVersionId? = null

    @RequiresNotEmpty
    var audits: MutableList<AuditDetails> = mutableListOf()
}