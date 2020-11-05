package org.openehr.rm.datatypes

import kotlinx.serialization.Serializable

/**
 * @author Primoz Delopst
 */

@Serializable
class DvCodedText : DvText() {
    lateinit var definingCode: CodePhrase
}