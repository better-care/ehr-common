package org.openehr.rm.datatypes

import kotlinx.serialization.Serializable

/**
 * @author Primoz Delopst
 */

@Serializable
class DvDateTime : DvTemporal() {
    lateinit var value: String
}