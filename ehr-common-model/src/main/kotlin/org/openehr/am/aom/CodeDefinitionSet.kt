package org.openehr.am.aom

import care.better.openehr.am.AmObject
import kotlinx.serialization.Serializable

/**
 * @author Primoz Delopst
 */

@Serializable
class CodeDefinitionSet : AmObject() {
    var items: MutableList<ArchetypeTerm> = mutableListOf()
    lateinit var language: String
}