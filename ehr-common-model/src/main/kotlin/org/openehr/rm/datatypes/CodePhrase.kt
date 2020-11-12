package org.openehr.rm.datatypes

import care.better.openehr.rm.RmObject
import care.better.platform.annotation.RequiresNotNull
import org.openehr.base.basetypes.TerminologyId
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

class CodePhrase : RmObject(), Serializable {
    @RequiresNotNull
    var terminologyId: TerminologyId? = null

    @RequiresNotNull
    var codeString: String? = null
}