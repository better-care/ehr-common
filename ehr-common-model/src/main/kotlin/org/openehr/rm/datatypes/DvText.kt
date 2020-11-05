package org.openehr.rm.datatypes

import kotlinx.serialization.Serializable

/**
 * @author Primoz Delopst
 */

@Serializable
open class DvText : DataValue() {
    lateinit var value: String
    var hyperlink: DvUri? = null
    var formatting: String? = null
    var mappings: MutableList<TermMapping> = mutableListOf()
    var language: CodePhrase? = null
    var encoding: CodePhrase? = null
}