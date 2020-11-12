package org.openehr.rm.datatypes

import care.better.openehr.rm.RmObject
import care.better.platform.annotation.RequiresNotNull
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

class TermMapping : RmObject(), Serializable {
    var match: String = "?"
    var purpose: DvCodedText? = null

    @RequiresNotNull
    var target: CodePhrase? = null
}