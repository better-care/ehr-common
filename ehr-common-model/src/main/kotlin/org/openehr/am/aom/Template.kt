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
import javax.xml.bind.annotation.*

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "OPERATIONAL_TEMPLATE", propOrder = [
        "language",
        "isControlled",
        "description",
        "revisionHistory",
        "uid",
        "templateId",
        "concept",
        "definition",
        "ontology",
        "componentOntologies",
        "annotations",
        "constraints",
        "view"])
@XmlRootElement
class Template : AmObject(), Serializable {
    companion object {
        private const val serialVersionUID: Long = 0L
    }

    @XmlElement(required = true)
    @Required
    var language: CodePhrase? = null

    @XmlElement(name = "is_controlled")
    var isControlled: Boolean? = null

    var description: ResourceDescription? = null

    @XmlElement(name = "revision_history")
    var revisionHistory: RevisionHistory? = null

    var uid: HierObjectId? = null

    @XmlElement(name = "template_id", required = true)
    lateinit var templateId: TemplateId

    @XmlElement(required = true)
    lateinit var concept: String

    @XmlElement(required = true, type = CArchetypeRoot::class)
    @Required
    var definition: CArchetypeRoot? = null

    @XmlElement(type = FlatArchetypeOntology::class)
    var ontology: FlatArchetypeOntology? = null

    @XmlElement(name = "component_ontologies", type = FlatArchetypeOntology::class)
    var componentOntologies: MutableList<FlatArchetypeOntology> = mutableListOf()

    @XmlElement(type = Annotation::class)
    var annotations: MutableList<Annotation> = mutableListOf()

    @XmlElement(type = TConstraints::class)
    var constraints: TConstraints? = null

    @XmlElement(type = TView::class)
    var view: TView? = null
}
