package org.openehr.rm.datatypes

import kotlinx.serialization.Serializable

/**
 * @author Primoz Delopst
 */

@Serializable
class DvParsable : DvEncapsulated() {
    lateinit var value: String
    lateinit var formalism: String
}