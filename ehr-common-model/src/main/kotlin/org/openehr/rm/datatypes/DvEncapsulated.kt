package org.openehr.rm.datatypes

/**
 * @author Primoz Delopst
 */

abstract class DvEncapsulated : DataValue() {
    var charset: CodePhrase? = null
    var language: CodePhrase? = null
}