package org.openehr.am.aom

import care.better.openehr.archetypemodel.AmObject
import kotlinx.serialization.Serializable
import org.openehr.rm.common.StringDictionaryItem

/**
 * @author Primoz Delopst
 */

@Serializable
class ArchetypeTerm : AmObject() {
    var items: MutableList<StringDictionaryItem> = mutableListOf()
    lateinit var code: String
}