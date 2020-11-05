package org.openehr.rm.datatypes

import care.better.openehr.rm.RmObject
import kotlinx.serialization.Serializable
import org.openehr.base.basetypes.TerminologyId

/**
 * @author Primoz Delopst
 */

@Serializable
class CodePhrase : RmObject() {
    lateinit var terminologyId: TerminologyId
    lateinit var codeString: String
}