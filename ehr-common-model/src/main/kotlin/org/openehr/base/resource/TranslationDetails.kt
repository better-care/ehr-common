package org.openehr.base.resource

import care.better.openehr.rm.RmObject
import org.openehr.rm.common.StringDictionaryItem
import org.openehr.rm.datatypes.CodePhrase
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

class TranslationDetails : RmObject(), Serializable {
    lateinit var language: CodePhrase
    var author: MutableList<StringDictionaryItem> = mutableListOf()
    var accreditation: String? = null
    var otherDetails: MutableList<StringDictionaryItem> = mutableListOf()
}