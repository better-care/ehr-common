package org.openehr.rm.datatypes

/**
 * @author Primoz Delopst
 */

class DvIdentifier : DataValue() {
    var issuer: String? = null
    var assigner: String? = null
    lateinit var id: String
    var type: String? = null
}