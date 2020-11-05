package org.openehr.am.aom

import kotlinx.serialization.Serializable
import org.openehr.base.basetypes.ArchetypeId
import org.openehr.base.basetypes.TemplateId

/**
 * @author Primoz Delopst
 */

@Serializable
class CArchetypeRoot : CComplexObject() {
    lateinit var archetypeId: ArchetypeId
    var templateId: TemplateId? = null
    var termDefinitions: MutableList<ArchetypeTerm> = mutableListOf()
    var termBindings: MutableList<TermBindingSet> = mutableListOf()
}