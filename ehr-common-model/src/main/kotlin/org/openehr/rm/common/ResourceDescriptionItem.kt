package org.openehr.rm.common

import care.better.openehr.rm.RmObject
import care.better.platform.annotation.RequiresNotNull
import org.openehr.rm.datatypes.CodePhrase
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

class ResourceDescriptionItem : RmObject(), Serializable {
    @RequiresNotNull
    var language: CodePhrase? = null

    @RequiresNotNull
    var purpose: String? = null
    var keywords: MutableList<String> = mutableListOf()
    var use: String? = null
    var misuse: String? = null
    var copyright: String? = null
    var originalResourceUri: MutableList<StringDictionaryItem> = mutableListOf()
    var otherDetails: MutableList<StringDictionaryItem> = mutableListOf()
}