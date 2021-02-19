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

import care.better.openehr.rm.RmObject
import care.better.platform.template.builder.CodedNameBuilder
import care.better.platform.template.builder.SimpleNameBuilder
import care.better.platform.template.builder.TermNameBuilder
import care.better.platform.template.context.AmNodeReference
import care.better.platform.template.context.ArchetypeNodeContext
import care.better.platform.template.exception.AmException
import care.better.platform.template.type.CollectionInfo
import care.better.platform.template.type.CollectionType
import care.better.platform.template.type.TypeInfo
import care.better.platform.utils.RmUtils
import care.better.platform.utils.exception.RmClassCastException
import care.better.platform.utils.exception.RmClassFieldNotFoundException
import org.openehr.am.aom.*
import org.openehr.am.aom.Annotation
import org.openehr.rm.datatypes.DataValue
import java.lang.reflect.Field

/**
 * @author Bostjan Lah
 * @author Primoz Delopst
 * @since 3.1.0
 *
 * Builder used to build [AmNode] from the template.
 *
 * @constructor Creates a new instance of [AmTreeBuilder]
 * @param template [Template]
 */
class AmTreeBuilder(private val template: Template) {

    companion object {
        private const val NAME_ATTRIBUTE = "name"
        private const val DEFINING_CODE_ATTRIBUTE = "defining_code"
    }

    /**
     * Builds and returns [AmNode] for the [Template].
     *
     * @return  [AmNode] for the [Template]
     */
    fun build(): AmNode =
        build(
            template.definition ?: throw AmException("Template ${template.templateId} does not have definition."),
            null,
            "",
            ArchetypeNodeContext.root()
        ).apply {
            template.language?.codeString?.also { this.templateLanguage = it }
            template.ontology?.also { copyOntology(it, this) }
            addAnnotations(this, template.annotations)
            addConstraints(this, template.constraints)
            addViewConstraints(this, template.view)
        }

    private fun build(cObject: CObject, parent: AmNode?, attributeName: String, archetypeNodeContext: ArchetypeNodeContext): AmNode {
        val amNode = AmNode(cObject, parent)
        val archetypeRoot = if (cObject is CArchetypeRoot) {
            amNode.terms = cObject.termDefinitions
            convertBindingItems(cObject)?.also { amNode.termBindings = it }
            findOntology(amNode)?.also { copyOntology(it, amNode) }
            true
        } else {
            false
        }

        val context: ArchetypeNodeContext = if (archetypeRoot) ArchetypeNodeContext(amNode, archetypeNodeContext) else archetypeNodeContext
        parent?.also { setGetterAndSetter(it, attributeName, amNode) }
        if (cObject is CComplexObject) {
            cObject.attributes.forEach {
                val name = it.rmAttributeName
                if (name != null) {
                    val amAttribute = buildAmAttribute(amNode, it, name, context)
                    val amAttributeName = it.rmAttributeName ?: throw AmException("RM attribute name is mandatory.")
                    amNode.attributes[amAttributeName] = amAttribute

                }
            }
        }

        if (cObject is ArchetypeInternalRef) {
            context.addReference(AmNodeReference(amNode, parent, attributeName, cObject.targetPath))
            return amNode
        }

        amNode.name = getName(amNode)
        addRmAttributes(amNode)
        if (archetypeRoot) {
            postProcessArchetypeNodeReferences(context)
        }
        return amNode
    }

    private fun postProcessArchetypeNodeReferences(archetypeNodeContext: ArchetypeNodeContext) {
        archetypeNodeContext.getReferences().forEach { context ->
            val archetypeRootNode = archetypeNodeContext.archetypeRootNode ?: throw AmException("Template can not be referenced.")
            val referencedNode: AmNode =
                AmUtils.resolvePath(archetypeRootNode, context.path) ?: throw AmException("Referenced AM node on path ${context.path} not found.")

            val amNode: AmNode = referencedNode.copyForReference(context.targetedAmNode ?: throw AmException("Target AM node not found"))
            context.targetedAmNode.attributes[context.attributeName]?.also {
                it.postProcessReference(context.referencedAmNode, amNode)
            }
        }
    }

    private fun setGetterAndSetter(parent: AmNode, attributeName: String, amNode: AmNode) =
        try {
            setGetterSetterAndType(amNode, attributeName, RmUtils.getRmClass(parent.rmType))
        } catch (ignored: RmClassCastException) {
        } catch (ignored: RmClassFieldNotFoundException) {
        }

    private fun addRmAttributes(amNode: AmNode) {
        try {
            val rmClass: Class<out RmObject?> = RmUtils.getRmClass(amNode.rmType)

            if (!DataValue::class.java.isAssignableFrom(rmClass)) {
                val requiredFields: Collection<Field> = RmUtils.getRequiredFields(amNode.rmType)
                for (field in RmUtils.getAllFields(amNode.rmType)) {
                    val attributeName: String = RmUtils.getAttributeForField(field.name)
                    if (!amNode.attributes.containsKey(attributeName)) {
                        val minExistence = if (requiredFields.contains(field)) 1 else 0
                        val child = AmNode(amNode, RmUtils.getRmTypeName(RmUtils.getFieldType(rmClass, field.name)), minExistence, 1)

                        setGetterSetterAndType(child, attributeName, rmClass)

                        child.name = attributeName
                        val amAttribute = AmAttribute(AmUtils.createInterval(minExistence, 1), mutableListOf(child))
                        amAttribute.rmOnly = true
                        amNode.attributes[attributeName] = amAttribute
                    }
                }
            }
        } catch (ignored: RmClassCastException) {
        } catch (ignored: RmClassFieldNotFoundException) {
        }
    }

    private fun setGetterSetterAndType(amNode: AmNode, attributeName: String, rmClass: Class<out RmObject?>) {
        amNode.setter = RmUtils.getSetterForAttribute(attributeName, rmClass)
        RmUtils.getGetterForAttribute(attributeName, rmClass)?.let {
            amNode.getter = it

            val returnType = it.returnType
            if (MutableCollection::class.java.isAssignableFrom(returnType)) {
                val collectionType = if (MutableList::class.java.isAssignableFrom(returnType)) CollectionType.LIST else CollectionType.SET
                amNode.setType(TypeInfo(RmUtils.getFieldType(rmClass, RmUtils.getFieldForAttribute(attributeName)), CollectionInfo(collectionType)))
            } else {
                amNode.setType(TypeInfo(returnType))
            }
        }
    }

    private fun findOntology(amNode: AmNode): FlatArchetypeOntology? =
        template.componentOntologies.firstOrNull { it.archetypeId == amNode.archetypeNodeId }


    private fun copyOntology(ontology: FlatArchetypeOntology, amNode: AmNode) {
        if (ontology.termDefinitions.isNotEmpty()) {
            amNode.termDefinitions = convertDefinitions(ontology.termDefinitions)
        }
        if (ontology.constraintDefinitions.isNotEmpty()) {
            amNode.constraintDefinitions = convertDefinitions(ontology.constraintDefinitions)
        }
    }

    private fun convertDefinitions(ontologyDefinitions: List<CodeDefinitionSet>): Map<String, Collection<ArchetypeTerm>> =
        ontologyDefinitions.associateBy({ it.language }, { it.items })

    private fun getName(amNode: AmNode): String? {
        val nameValueNode: AmNode? = AmUtils.getAmNode(amNode, NAME_ATTRIBUTE, AmUtils.VALUE_ATTRIBUTE)
        val nameBuilder =
            if (nameValueNode == null) {
                val definingCodeNode: AmNode? = AmUtils.getAmNode(amNode, NAME_ATTRIBUTE, DEFINING_CODE_ATTRIBUTE)
                if (definingCodeNode == null) TermNameBuilder() else CodedNameBuilder(definingCodeNode.cObject)
            } else {
                SimpleNameBuilder(nameValueNode.cObject)
            }
        return nameBuilder.getName(amNode)
    }

    private fun buildAmAttribute(parent: AmNode, attribute: CAttribute, attributeName: String, archetypeNodeContext: ArchetypeNodeContext): AmAttribute {
        val children: MutableList<AmNode> = ArrayList()
        for (child in attribute.children) {
            children.add(build(child, parent, attributeName, archetypeNodeContext))
        }
        val amAttribute = AmAttribute(attribute.existence, children)
        if (attribute is CMultipleAttribute) {
            amAttribute.cardinality = attribute.cardinality
        }
        return amAttribute
    }

    private fun addAnnotations(amNode: AmNode, annotations: List<Annotation>) {
        convertAnnotations(amNode, annotations).forEach { (key, value) -> key.annotations = value }
    }

    private fun convertAnnotations(root: AmNode, annotations: List<Annotation>): Map<AmNode, MutableList<Annotation>> {
        val mappedAnnotations: MutableMap<AmNode, MutableList<Annotation>> = HashMap()
        for (annotation in annotations) {
            val node: AmNode? = AmUtils.resolvePath(root, annotation.path)
            if (node != null) {
                val nodeAnnotations = mappedAnnotations.computeIfAbsent(node) { mutableListOf() }
                nodeAnnotations.add(annotation)
            }
        }
        return mappedAnnotations
    }

    private fun addConstraints(amNode: AmNode, constraints: TConstraints?) {
        if (constraints != null) {
            convertConstraints(amNode, constraints).forEach { (key, value) -> key.constraints = value }
        }
    }

    private fun convertConstraints(root: AmNode, constraints: TConstraints): Map<AmNode, MutableList<TAttribute>> {
        val mappedConstraints: MutableMap<AmNode, MutableList<TAttribute>> = HashMap()
        for (attribute in constraints.attributes) {
            val path = if (attribute.rmAttributeName == null) attribute.differentialPath else attribute.differentialPath + '/' + attribute.rmAttributeName
            val node: AmNode? = AmUtils.resolvePath(root, path)
            if (node != null) {
                val nodeAttributes = mappedConstraints.computeIfAbsent(node) { mutableListOf() }
                nodeAttributes.add(attribute)
            }
        }
        return mappedConstraints
    }

    private fun addViewConstraints(amNode: AmNode, tView: TView?) {
        if (tView != null) {
            convertViewConstraints(amNode, tView).forEach { (key, value) -> key.viewConstraints = value }
        }
    }

    private fun convertViewConstraints(root: AmNode, tView: TView): Map<AmNode, MutableList<TView.Constraints.Items>> {
        val mappedConstraints: MutableMap<AmNode, MutableList<TView.Constraints.Items>> = HashMap()
        for (viewConstraint in tView.constraints) {
            if (viewConstraint.items.isNotEmpty()) {
                val node: AmNode? = AmUtils.resolvePath(root, viewConstraint.path)
                if (node != null) {
                    val itemsList = mappedConstraints.computeIfAbsent(node) { mutableListOf() }
                    itemsList.addAll(viewConstraint.items)
                }
            }
        }
        return mappedConstraints
    }

    private fun convertBindingItems(cArchetypeRoot: CArchetypeRoot): Map<String, Collection<TermBindingItem>>? {
        return if (cArchetypeRoot.termBindings.isNotEmpty()) {
            val result: MutableMap<String, Collection<TermBindingItem>> = LinkedHashMap(cArchetypeRoot.termBindings.size)
            for (termBindingSet in cArchetypeRoot.termBindings) {
                val items: List<TermBindingItem> = termBindingSet.items.toList()
                result[termBindingSet.terminology] = items
            }
            result
        } else {
            null
        }
    }
}
