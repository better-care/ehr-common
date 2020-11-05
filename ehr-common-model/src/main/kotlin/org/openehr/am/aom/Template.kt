package org.openehr.am.aom

import care.better.openehr.am.AmObject
import kotlinx.serialization.Serializable
import org.openehr.base.basetypes.HierObjectId
import org.openehr.base.basetypes.TemplateId
import org.openehr.rm.common.ResourceDescription
import org.openehr.rm.common.RevisionHistory
import org.openehr.rm.datatypes.CodePhrase

/**
 * @author Primoz Delopst
 */

@Serializable
class Template : AmObject() {
    lateinit var language: CodePhrase
    var isControlled: Boolean? = null
    var description: ResourceDescription? = null
    var revisionHistory: RevisionHistory? = null
    var uid: HierObjectId? = null
    lateinit var templateId: TemplateId
    lateinit var concept: String
    lateinit var definition: CArchetypeRoot
    var ontology: FlatArchetypeOntology? = null
    var componentOntologies: MutableList<FlatArchetypeOntology> = mutableListOf()
    var annotations: MutableList<Annotation> = mutableListOf()
    var constraints: TConstraints? = null
    var view: TView? = null
}