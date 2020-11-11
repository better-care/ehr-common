package org.openehr.rm.datatypes

/**
 * @author Primoz Delopst
 */

open class DvText : DataValue() {
    lateinit var value: String
    var hyperlink: DvUri? = null
    var formatting: String? = null
    var mappings: MutableList<TermMapping> = mutableListOf()
    var language: CodePhrase? = null
    var encoding: CodePhrase? = null
}