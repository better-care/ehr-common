package org.openehr.am.aom

import care.better.openehr.am.AmObject
import care.better.platform.annotation.RequiresNotNull
import org.openehr.base.basetypes.HierObjectId
import org.openehr.base.basetypes.TemplateId
import org.openehr.rm.common.ResourceDescription
import org.openehr.rm.common.RevisionHistory
import org.openehr.rm.datatypes.CodePhrase
import java.io.Serializable

/**
 * @author Primoz Delopst
 */

class Template : AmObject(), Serializable {
    @RequiresNotNull
    var language: CodePhrase? = null
    var isControlled: Boolean? = null
    var description: ResourceDescription? = null
    var revisionHistory: RevisionHistory? = null
    var uid: HierObjectId? = null

    @RequiresNotNull
    var templateId: TemplateId? = null

    @RequiresNotNull
    var concept: String? = null

    @RequiresNotNull
    var definition: CArchetypeRoot? = null
    var ontology: FlatArchetypeOntology? = null
    var componentOntologies: MutableList<FlatArchetypeOntology> = mutableListOf()
    var annotations: MutableList<Annotation> = mutableListOf()
    var constraints: TConstraints? = null
    var view: TView? = null
}