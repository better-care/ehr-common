package org.openehr.am.aom

import org.openehr.base.basetypes.ArchetypeId
import org.openehr.base.basetypes.TemplateId

/**
 * @author Primoz Delopst
 */

class CArchetypeRoot : CComplexObject() {
    lateinit var archetypeId: ArchetypeId
    var templateId: TemplateId? = null
    var termDefinitions: MutableList<ArchetypeTerm> = mutableListOf()
    var termBindings: MutableList<TermBindingSet> = mutableListOf()
}