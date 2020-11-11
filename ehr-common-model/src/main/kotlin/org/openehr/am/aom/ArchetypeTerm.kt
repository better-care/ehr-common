package org.openehr.am.aom

import care.better.openehr.am.AmObject
import org.openehr.rm.common.StringDictionaryItem
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

class ArchetypeTerm : AmObject(), Serializable {
    var items: MutableList<StringDictionaryItem> = mutableListOf()
    lateinit var code: String
}