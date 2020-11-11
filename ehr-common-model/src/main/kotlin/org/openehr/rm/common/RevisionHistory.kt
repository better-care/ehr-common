package org.openehr.rm.common

import care.better.openehr.rm.RmObject
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

class RevisionHistory : RmObject(), Serializable {
    var items: MutableList<RevisionHistoryItem> = mutableListOf()
}