package org.openehr.rm.common

import care.better.openehr.rm.RmObject
import org.openehr.rm.datatypes.CodePhrase
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

class ResourceDescriptionItem : RmObject(), Serializable {
    lateinit var language: CodePhrase
    lateinit var purpose: String
    var keywords: MutableList<String> = mutableListOf()
    var use: String? = null
    var misuse: String? = null
    var copyright: String? = null
    var originalResourceUri: MutableList<StringDictionaryItem> = mutableListOf()
    var otherDetails: MutableList<StringDictionaryItem> = mutableListOf()
}