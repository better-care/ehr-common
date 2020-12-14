/* Copyright 2021 Better Ltd (www.better.care)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openehr.am.aom

import care.better.openehr.am.AmObject
import care.better.platform.annotation.Required
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
    @Required
    var language: CodePhrase? = null
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