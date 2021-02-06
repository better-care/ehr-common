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

package care.better.platform.template

import care.better.platform.template.exception.AmException
import care.better.platform.template.type.CollectionType
import care.better.platform.template.type.TypeInfo
import org.openehr.am.aom.*
import org.openehr.am.aom.Annotation
import org.openehr.base.foundationtypes.IntervalOfInteger
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

/**
 * @author Bostjan Lah
 * @author Primoz Delopst
 * @since 3.1.0
 */
@Suppress("SpellCheckingInspection")
class AmNode constructor(
    val parent: AmNode? = null,
    val cObject: CObject? = null,
    val archetypeNodeId: String? = null,
    var nodeId: String? = null,
    val rmType: String,
    var name: String? = null,
    private var terms: List<ArchetypeTerm>? = null,
    private var termDefinitions: Map<String, Collection<ArchetypeTerm>>? = null,
    private var constraintDefinitions: Map<String, Collection<ArchetypeTerm>>? = null,
    private var termBindings: Map<String, Collection<TermBindingItem>>? = null,
    val attributes: LinkedHashMap<String, AmAttribute> = linkedMapOf(),
    var occurrences: IntervalOfInteger? = null,
    var getter: Method? = null,
    var setter: Method? = null,
    var type: TypeInfo? = null,
    var constraints: List<TAttribute>? = null,
    var annotations: List<Annotation>? = null,
    var viewConstraints: List<TView.Constraints.Items>? = null,
    private var templateLanguage: String? = null) {

    constructor(cObject: CObject, parent: AmNode?) : this(
        parent = parent,
        cObject = cObject,
        archetypeNodeId = if (cObject is CArchetypeRoot) cObject.archetypeId.value else cObject.nodeId,
        rmType = cObject.rmTypeName ?: throw AmException("RM type name is mandatory."),
        nodeId = cObject.nodeId,
        occurrences = cObject.occurrences ?: AmUtils.createInterval(0, null)
    )

    constructor(parent: AmNode?, rmType: String) : this(parent, rmType, 1, null)

    constructor(parent: AmNode?, rmType: String, minOccurences: Int, maxOccurences: Int?) : this(
        parent = parent,
        rmType = rmType,
        occurrences = AmUtils.createInterval(minOccurences, maxOccurences)
    )


    fun getTerms(): List<ArchetypeTerm>? = terms ?: parent?.getTerms()

    fun setTerms(terms: List<ArchetypeTerm>) {
        this.terms = terms
    }

    fun getTermDefinitions(): Map<String, Collection<ArchetypeTerm>>? = termDefinitions ?: parent?.getTermDefinitions()

    fun setTermDefinitions(termDefinitions: Map<String, Collection<ArchetypeTerm>>) {
        this.termDefinitions = termDefinitions
    }

    fun getConstraintDefinitions(): Map<String, Collection<ArchetypeTerm>>? = constraintDefinitions ?: parent?.getConstraintDefinitions()

    fun setConstraintDefinitions(constraintDefinitions: Map<String, Collection<ArchetypeTerm>>) {
        this.constraintDefinitions = constraintDefinitions
    }

    fun getTermBindings(): Map<String, Collection<TermBindingItem>>? = termBindings ?: parent?.getTermBindings()

    fun setTermBindings(termBindings: Map<String, Collection<TermBindingItem>>) {
        this.termBindings = termBindings
    }

    fun getTermBindings(atCode: String): Map<String, String> =
        with(getTermBindings()) {
            this?.entries?.asSequence()
                ?.flatMap { it.value.asSequence() }
                ?.filter { atCode == it.code }?.map { it }
                ?.associateBy(
                    { it.value.terminologyId?.value ?: throw AmException("Terminology must be set.") },
                    { it.value.codeString ?: throw AmException("Code must be set.") }) ?: emptyMap()
        }

    fun getOnParent(parent: Any) =
        try {
            if (getter != null)
                getter?.invoke(parent)
            else
                throw AmException("Getter for $this  not found.")
        } catch (ex: IllegalAccessException) {
            throw AmException(ex)
        } catch (ex: InvocationTargetException) {
            throw AmException(ex)
        }

    fun setOnParent(parent: Any, value: Any?) {
        try {
            setter?.invoke(parent, value) ?: throw AmException("Setter for $this  not found.")
        } catch (ex: IllegalAccessException) {
            throw AmException(ex)
        } catch (ex: InvocationTargetException) {
            throw AmException(ex)
        }
    }

    fun getTypeOnParent(): TypeInfo? = type

    fun isCollectionOnParent(): Boolean = type?.isCollection() ?: false

    fun createMutableCollection(): MutableCollection<Any> =
        when {
            type?.collection == null || CollectionType.LIST == type?.collection?.collectionType -> mutableListOf()
            else -> mutableSetOf()
        }

    fun getTemplateLangugage(): String? = if (templateLanguage == null && parent != null) parent.getTemplateLangugage() else templateLanguage

    fun setTemplateLanguage(templateLanguage: String) {
        this.templateLanguage = templateLanguage
    }

    fun copyForReference(parent: AmNode?): AmNode {
        val amNode = if (cObject == null) AmNode(parent, rmType) else AmNode(cObject, parent)

        amNode.nodeId = nodeId
        amNode.name = name
        amNode.terms = terms
        amNode.termDefinitions = termDefinitions
        amNode.constraintDefinitions = constraintDefinitions
        amNode.termBindings = termBindings
        amNode.occurrences = occurrences
        amNode.getter = getter
        amNode.setter = setter
        amNode.annotations = annotations
        amNode.type = type
        amNode.viewConstraints = viewConstraints
        amNode.templateLanguage = templateLanguage

        attributes.entries.forEach {
            amNode.attributes[it.key] = AmAttribute(it.value.existence, it.value.getChildren().map { child -> child.copyForReference(amNode) })
        }
        return amNode
    }

}
