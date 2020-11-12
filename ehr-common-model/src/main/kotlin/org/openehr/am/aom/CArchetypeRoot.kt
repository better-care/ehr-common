package org.openehr.am.aom

import care.better.platform.annotation.RequiresNotNull
import org.openehr.base.basetypes.ArchetypeId
import org.openehr.base.basetypes.TemplateId

/**
 * @author Primoz Delopst
 */

class CArchetypeRoot : CComplexObject() {
    @RequiresNotNull
    var archetypeId: ArchetypeId? = null
    var templateId: TemplateId? = null
    var termDefinitions: MutableList<ArchetypeTerm> = mutableListOf()
    var termBindings: MutableList<TermBindingSet> = mutableListOf()
}