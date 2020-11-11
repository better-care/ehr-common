package org.openehr.rm.datatypes

import care.better.openehr.rm.RmObject
import org.openehr.base.basetypes.TerminologyId
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

class CodePhrase : RmObject(), Serializable {
    lateinit var terminologyId: TerminologyId
    lateinit var codeString: String
}