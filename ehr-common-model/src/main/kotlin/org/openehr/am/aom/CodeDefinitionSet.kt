package org.openehr.am.aom

import care.better.openehr.am.AmObject
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

class CodeDefinitionSet : AmObject(), Serializable {
    var items: MutableList<ArchetypeTerm> = mutableListOf()
    lateinit var language: String
}