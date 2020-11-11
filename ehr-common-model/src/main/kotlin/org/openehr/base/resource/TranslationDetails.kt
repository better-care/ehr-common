package org.openehr.base.resource

import care.better.openehr.rm.RmObject
import care.better.platform.annotation.RequiresNotEmpty
import org.openehr.rm.common.StringDictionaryItem
import org.openehr.rm.datatypes.CodePhrase
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

class TranslationDetails : RmObject(), Serializable {
    lateinit var language: CodePhrase
    @RequiresNotEmpty
    var author: MutableList<StringDictionaryItem> = mutableListOf()
    var accreditation: String? = null
    var otherDetails: MutableList<StringDictionaryItem> = mutableListOf()
}