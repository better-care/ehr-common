package org.openehr.rm.common

import care.better.openehr.referencemodel.RmObject
import kotlinx.serialization.Serializable

/**
 * @author Primoz Delopst
 */

@Serializable
class RevisionHistory : RmObject() {
    var items: MutableList<RevisionHistoryItem> = mutableListOf()
}