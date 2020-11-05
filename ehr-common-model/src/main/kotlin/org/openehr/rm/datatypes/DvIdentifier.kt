package org.openehr.rm.datatypes

import kotlinx.serialization.Serializable

/**
 * @author Primoz Delopst
 */

@Serializable
class DvIdentifier : DataValue() {
    var issuer: String? = null
    var assigner: String? = null
    lateinit var id: String
    var type: String? = null
}