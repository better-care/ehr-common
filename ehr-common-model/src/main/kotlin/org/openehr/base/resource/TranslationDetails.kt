package org.openehr.base.resource

import care.better.openehr.rm.RmObject
import kotlinx.serialization.Serializable
import org.openehr.rm.common.StringDictionaryItem
import org.openehr.rm.datatypes.CodePhrase

/**
 * @author Primoz Delopst
 */

@Serializable
class TranslationDetails : RmObject(){
    lateinit var language: CodePhrase
    var author: MutableList<StringDictionaryItem> = mutableListOf()
    var accreditation: String? = null
    var otherDetails: MutableList<StringDictionaryItem> = mutableListOf()
}