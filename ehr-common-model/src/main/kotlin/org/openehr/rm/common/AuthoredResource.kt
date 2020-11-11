package org.openehr.rm.common

import care.better.openehr.rm.RmObject
import org.openehr.base.resource.TranslationDetails
import org.openehr.rm.datatypes.CodePhrase
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

abstract class AuthoredResource : RmObject(), Serializable {
    lateinit var originalLanguage: CodePhrase
    var isControlled: Boolean? = null
    var description: ResourceDescription? = null
    var translations: MutableList<TranslationDetails> = mutableListOf()
    var revisionHistory: RevisionHistory? = null
}