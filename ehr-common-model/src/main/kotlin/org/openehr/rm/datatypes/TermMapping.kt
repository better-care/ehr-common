package org.openehr.rm.datatypes

import care.better.openehr.rm.RmObject
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

class TermMapping : RmObject(), Serializable {
    var match: String = "?"
    var purpose: DvCodedText? = null
    lateinit var target: CodePhrase
}