package org.openehr.rm.datatypes

import care.better.platform.annotation.RequiresNotNull

/**
 * @author Primoz Delopst
 */

open class DvText : DataValue() {
    @RequiresNotNull
    var value: String? = null
    var hyperlink: DvUri? = null
    var formatting: String? = null
    var mappings: MutableList<TermMapping> = mutableListOf()
    var language: CodePhrase? = null
    var encoding: CodePhrase? = null
}