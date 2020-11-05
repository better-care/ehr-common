package org.openehr.rm.common

import care.better.openehr.referencemodel.RmObject
import kotlinx.serialization.Serializable
import org.openehr.base.resource.TranslationDetails
import org.openehr.rm.datatypes.CodePhrase

/**
 * @author Primoz Delopst
 */

@Serializable
abstract class AuthoredResource : RmObject() {
    lateinit var originalLanguage: CodePhrase
    var isControlled: Boolean? = null
    var description: ResourceDescription? = null
    var translations: MutableList<TranslationDetails> = mutableListOf()
    var revisionHistory: RevisionHistory? = null
}