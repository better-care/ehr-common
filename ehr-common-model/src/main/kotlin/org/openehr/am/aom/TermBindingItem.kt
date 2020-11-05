package org.openehr.am.aom

import care.better.openehr.archetypemodel.AmObject
import kotlinx.serialization.Serializable
import org.openehr.rm.datatypes.CodePhrase

/**
 * @author Primoz Delopst
 */

@Serializable
class TermBindingItem : AmObject() {
    lateinit var value: CodePhrase
    lateinit var code: String
}