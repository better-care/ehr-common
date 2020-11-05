package org.openehr.rm.datatypes

import care.better.openehr.referencemodel.RmObject
import kotlinx.serialization.Serializable

/**
 * @author Primoz Delopst
 */

@Serializable
class TermMapping : RmObject() {
    var match: String = "?"
    var purpose: DvCodedText? = null
    lateinit var target: CodePhrase
}