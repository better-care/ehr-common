package org.openehr.rm.common

import care.better.openehr.rm.RmObject
import kotlinx.serialization.Serializable

/**
 * @author Primoz Delopst
 */

@Serializable
class RevisionHistory : RmObject() {
    var items: MutableList<RevisionHistoryItem> = mutableListOf()
}