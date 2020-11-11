package org.openehr.am.aom

import care.better.openehr.am.AmObject
import care.better.platform.annotation.RequiresNotEmpty
import org.openehr.rm.common.StringDictionaryItem
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

class ArchetypeTerm : AmObject(), Serializable {
    @RequiresNotEmpty
    var items: MutableList<StringDictionaryItem> = mutableListOf()
    lateinit var code: String
}