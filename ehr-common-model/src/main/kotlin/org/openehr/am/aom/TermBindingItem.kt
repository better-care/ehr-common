package org.openehr.am.aom

import care.better.openehr.am.AmObject
import care.better.platform.annotation.RequiresNotNull
import org.openehr.rm.datatypes.CodePhrase
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

class TermBindingItem : AmObject(), Serializable {
    @RequiresNotNull
    var value: CodePhrase? = null

    @RequiresNotNull
    var code: String? = null
}