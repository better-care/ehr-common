package org.openehr.rm.datatypes

import kotlinx.serialization.Serializable

/**
 * @author Primoz Delopst
 */

@Serializable
abstract class DvEncapsulated : DataValue() {
    var charset: CodePhrase? = null
    var language: CodePhrase? = null
}