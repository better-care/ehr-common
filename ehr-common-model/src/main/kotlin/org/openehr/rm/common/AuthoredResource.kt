package org.openehr.rm.common

import care.better.openehr.rm.RmObject
import care.better.platform.annotation.RequiresNotNull
import org.openehr.base.resource.TranslationDetails
import org.openehr.rm.datatypes.CodePhrase
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

abstract class AuthoredResource : RmObject(), Serializable {
    @RequiresNotNull
    var originalLanguage: CodePhrase? = null
    var isControlled: Boolean? = null
    var description: ResourceDescription? = null
    var translations: MutableList<TranslationDetails> = mutableListOf()
    var revisionHistory: RevisionHistory? = null
}