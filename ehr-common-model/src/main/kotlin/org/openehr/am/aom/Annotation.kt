package org.openehr.am.aom

import care.better.openehr.archetypemodel.AmObject
import kotlinx.serialization.Serializable
import org.openehr.rm.common.StringDictionaryItem

/**
 * @author Primoz Delopst
 */

@Serializable
class Annotation : AmObject() {
    var items: MutableList<StringDictionaryItem> = mutableListOf()
    lateinit var path: String
}