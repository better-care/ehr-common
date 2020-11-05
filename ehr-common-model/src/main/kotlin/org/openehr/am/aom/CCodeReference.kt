package org.openehr.am.aom

import kotlinx.serialization.Serializable

/**
 * @author Primoz Delopst
 */

@Serializable
class CCodeReference : CCodePhrase() {
    lateinit var referenceSetUri: String
}