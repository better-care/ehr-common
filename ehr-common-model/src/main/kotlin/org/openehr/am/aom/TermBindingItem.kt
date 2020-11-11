package org.openehr.am.aom

import care.better.openehr.am.AmObject
import org.openehr.rm.datatypes.CodePhrase
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

class TermBindingItem : AmObject(), Serializable {
    lateinit var value: CodePhrase
    lateinit var code: String
}