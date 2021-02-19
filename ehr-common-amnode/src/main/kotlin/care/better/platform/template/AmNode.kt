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
import care.better.platform.template.type.TypeInfo
import org.apache.commons.lang3.builder.ToStringBuilder
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
class AmNode private constructor(
    val parent: AmNode? = null,
    val cObject: CObject? = null,
    val archetypeNodeId: String? = null,
    var nodeId: String? = null,
    val rmType: String,
    var name: String? = null,
    val attributes: LinkedHashMap<String, AmAttribute> = linkedMapOf(),
    var occurrences: IntervalOfInteger,
    var getter: Method? = null,
    var setter: Method? = null,
    private var type: TypeInfo? = null,
    var constraints: List<TAttribute>? = null,
    var annotations: List<Annotation>? = null,
    var viewConstraints: List<TView.Constraints.Items>? = null) {

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

    private var _terms: List<ArchetypeTerm>? = null
    var terms: List<ArchetypeTerm>
        get() = _terms ?: parent?.terms ?: emptyList()
        set(value) {
            _terms = value
        }

    private var _termDefinitions: Map<String, Collection<ArchetypeTerm>>? = null
    var termDefinitions: Map<String, Collection<ArchetypeTerm>>
        get() = _termDefinitions ?: parent?.termDefinitions ?: emptyMap()
        set(value) {
            _termDefinitions = value
        }

    private var _constraintDefinitions: Map<String, Collection<ArchetypeTerm>>? = null
    var constraintDefinitions: Map<String, Collection<ArchetypeTerm>>
        get() = _constraintDefinitions ?: parent?.constraintDefinitions ?: emptyMap()
        set(value) {
            _constraintDefinitions = value
        }

    var templateLanguage: String? = null
        get() = field ?: parent?.templateLanguage

    private var _termBindings: Map<String, Collection<TermBindingItem>>? = null
    var termBindings: Map<String, Collection<TermBindingItem>>
        get() = _termBindings ?: parent?.termBindings ?: emptyMap()
        set(value) {
            _termBindings = value
        }


    fun getTermBindings(atCode: String): Map<String, String> =
        with(termBindings) {
            this.entries.asSequence()
                .flatMap { it.value.asSequence() }
                .filter { atCode == it.code }.map { it }
                .associateBy(
                    { it.value.terminologyId?.value ?: throw AmException("Terminology must be set.") },
                    { it.value.codeString ?: throw AmException("Code must be set.") })
        }

    fun setType(type: TypeInfo?) {
        this.type = type
    }

    fun isPropertyOnParent(): Boolean {
        return getter != null
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
            if (setter != null)
                setter?.invoke(parent, value)
            else
                throw AmException("Setter for $this  not found.")
        } catch (ex: IllegalAccessException) {
            throw AmException(ex)
        } catch (ex: InvocationTargetException) {
            throw AmException(ex)
        }
    }

    fun getTypeOnParentOrNull(): TypeInfo? = type

    fun getTypeOnParent(): TypeInfo = type ?: throw AmException("Type for $this not found.")

    fun isCollectionOnParent(): Boolean = type?.isCollection() ?: false

    internal fun copyForReference(parent: AmNode?): AmNode {
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
            amNode.attributes[it.key] = AmAttribute(it.value.existence, it.value.children.map { child -> child.copyForReference(amNode) })
        }
        return amNode
    }

    override fun toString(): String =
        ToStringBuilder(this)
            .append("archetypeNodeId", archetypeNodeId)
            .append("rmType", rmType)
            .append("name", name)
            .toString()
}
