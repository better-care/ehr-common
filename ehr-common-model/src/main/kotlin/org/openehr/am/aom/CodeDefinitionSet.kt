package org.openehr.am.aom

import care.better.openehr.am.AmObject
import care.better.platform.annotation.RequiresNotNull
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

class CodeDefinitionSet : AmObject(), Serializable {
    var items: MutableList<ArchetypeTerm> = mutableListOf()
    @RequiresNotNull
    var language: String? = null
}