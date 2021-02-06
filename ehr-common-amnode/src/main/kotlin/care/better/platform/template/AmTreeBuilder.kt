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
import org.openehr.rm.datatypes.DataValue
import java.lang.reflect.Field
import java.lang.reflect.Method

/**
 * @author Bostjan Lah
 * @author Primoz Delopst
 * @since 3.1.0
 */
class AmTreeBuilder(private val template: Template) {

    companion object {
        private const val NAME_ATTRIBUTE = "name"
        private const val DEFINING_CODE_ATTRIBUTE = "defining_code"
    }

    fun build(): AmNode =
        build(template.definition ?: throw AmException("Template ${template.templateId} does not have definition."), null, "", ArchetypeNodeContext.root()).apply {
            template.language?.codeString?.also { this.setTemplateLanguage(it) }
            template.ontology?.also { copyOntology(it, this) }
            addAnnotations(this, template.annotations)
            addConstraints(this, template.constraints)
            addViewConstraints(this, template.view)
        }

    private fun build(cObject: CObject, parent: AmNode?, attributeName: String, archetypeNodeContext: ArchetypeNodeContext): AmNode {
        val amNode = AmNode(cObject, parent)
        val archetypeRoot = cObject is CArchetypeRoot
        if (archetypeRoot) {
            val cArchetypeRoot = cObject as CArchetypeRoot
            amNode.setTerms(cArchetypeRoot.termDefinitions)
            convertBindingItems(cArchetypeRoot)?.also { amNode.setTermBindings(it) }
            findOntology(amNode)?.also { copyOntology(it, amNode) }
        }


        val context: ArchetypeNodeContext = if (archetypeRoot) ArchetypeNodeContext(amNode, archetypeNodeContext) else archetypeNodeContext
        parent?.also { setGetterAndSetter(it, attributeName, amNode) }
        if (cObject is CComplexObject) {
            cObject.attributes.forEach {
                if (it.rmAttributeName != null) {
                    val amAttribute = buildAmAttribute(amNode, it, it.rmAttributeName, context)
                    val amAttributeName = it.rmAttributeName ?:  throw AmException("RM attribute name is mandatory.")
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
            val amNode: AmNode = AmUtils.resolvePath(archetypeNodeContext.archetypeRootNode, context.path).copyForReference(context.targetedAmNode)
            context.targetedAmNode?.attributes?.get(context.attributeName)?.also {
                it.postProcessReference(context.referencedAmNode, amNode)
            }
        }
    }

    private fun setGetterAndSetter(parent: AmNode, attributeName: String, amNode: AmNode) {
        try {
            val rmClass: Class<out RmObject?> = RmUtils.getRmClass(parent.rmType)
            val getter: Method? = RmUtils.getGetterForAttribute(attributeName, rmClass)

            amNode.getter = getter
            amNode.setter = RmUtils.getSetterForAttribute(attributeName, rmClass)

            if (getter != null) {
                val returnType = getter.returnType
                if (MutableCollection::class.java.isAssignableFrom(returnType)) {
                    val collectionType = if (MutableList::class.java.isAssignableFrom(returnType)) CollectionType.LIST else CollectionType.SET
                    amNode.type = TypeInfo(RmUtils.getFieldType(rmClass, RmUtils.getFieldForAttribute(attributeName)), CollectionInfo(collectionType))
                } else {
                    amNode.type = TypeInfo(returnType)
                }
            }
        } catch (ignored: RmClassCastException) {
        } catch (ignored: RmClassFieldNotFoundException) {
        }
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

                        val getter: Method? = RmUtils.getGetterForAttribute(attributeName, rmClass)
                        child.getter = getter
                        child.setter = RmUtils.getSetterForAttribute(attributeName, rmClass)

                        if (getter != null) {
                            val returnType = getter.returnType
                            if (MutableCollection::class.java.isAssignableFrom(returnType)) {
                                val collectionType = if (MutableList::class.java.isAssignableFrom(returnType)) CollectionType.LIST else CollectionType.SET
                                child.type = TypeInfo(RmUtils.getFieldType(rmClass, RmUtils.getFieldForAttribute(attributeName)), CollectionInfo(collectionType))
                            } else {
                                child.type = TypeInfo(returnType)
                            }
                        }

                        child.name = attributeName
                        val amAttribute = AmAttribute(AmUtils.createInterval(minExistence, 1), com.google.common.collect.Lists.newArrayList(child))
                        amAttribute.setRmOnly(true)
                        amNode.attributes.put(attributeName, amAttribute)
                    }
                }
            }
        } catch (ignored: RmClassCastException) {
        } catch (ignored: RmClassFieldNotFoundException) {
        }
    }

    private fun findOntology(amNode: AmNode): FlatArchetypeOntology? =
        template.componentOntologies.firstOrNull { it.archetypeId == amNode.archetypeNodeId }


    private fun copyOntology(ontology: FlatArchetypeOntology, amNode: AmNode) {
        if (ontology.termDefinitions.isNotEmpty()) {
            amNode.setTermDefinitions(convertDefinitions(ontology.termDefinitions))
        }
        if (ontology.constraintDefinitions.isNotEmpty()) {
            amNode.setConstraintDefinitions(convertDefinitions(ontology.constraintDefinitions))
        }
    }

    private fun convertDefinitions(ontologyDefinitions: List<CodeDefinitionSet>): Map<String, Collection<ArchetypeTerm>> =
        ontologyDefinitions.associateBy({it.language}, {it.items})

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

    private fun buildAmAttribute(parent: AmNode, attribute: CAttribute, attributeName: String?, archetypeNodeContext: ArchetypeNodeContext): AmAttribute {
        val children: MutableList<AmNode> = ArrayList()
        for (`object` in attribute.children) {
            children.add(build(`object`, parent, attributeName, archetypeNodeContext))
        }
        val amAttribute = AmAttribute(attribute.existence!!, children)
        if (attribute is CMultipleAttribute) {
            amAttribute.setCardinality(attribute.cardinality!!)
        }
        return amAttribute
    }

    private fun addAnnotations(amNode: AmNode, annotations: List<Annotation>) {
        for ((key, value) in convertAnnotations(amNode, annotations)) {
            key.setAnnotations(value)
        }
    }

    private fun convertAnnotations(root: AmNode, annotations: List<Annotation>): Map<AmNode, MutableList<Annotation>> {
        val mappedAnnotations: MutableMap<AmNode, MutableList<Annotation>> = HashMap()
        for (annotation in annotations) {
            val node: AmNode = AmUtils.resolvePath(root, annotation.path)
            if (node != null) {
                val nodeAnnotations = mappedAnnotations.computeIfAbsent(
                    node
                ) { ignore: AmNode? -> ArrayList() }
                nodeAnnotations.add(annotation)
            }
        }
        return mappedAnnotations
    }

    private fun addConstraints(amNode: AmNode, constraints: TConstraints?) {
        if (constraints != null) {
            for ((key, value) in convertConstraints(amNode, constraints)) {
                key.setConstraints(value)
            }
        }
    }

    private fun convertConstraints(root: AmNode, constraints: TConstraints): Map<AmNode, MutableList<TAttribute>> {
        val mappedConstraints: MutableMap<AmNode, MutableList<TAttribute>> = HashMap()
        for (attribute in constraints.attributes) {
            val path = if (attribute.rmAttributeName == null) attribute.differentialPath else attribute.differentialPath + '/' + attribute.rmAttributeName
            val node: AmNode = AmUtils.resolvePath(root, path)
            if (node != null) {
                val nodeAttributes = mappedConstraints.computeIfAbsent(
                    node
                ) { k: AmNode? -> ArrayList() }
                nodeAttributes.add(attribute)
            }
        }
        return mappedConstraints
    }

    private fun addViewConstraints(amNode: AmNode, tView: TView?) {
        if (tView != null) {
            for ((key, value) in convertViewConstraints(amNode, tView)) {
                key.setViewConstraints(value)
            }
        }
    }

    private fun convertViewConstraints(root: AmNode, tView: TView): Map<AmNode, MutableList<TView.Constraints.Items>> {
        val mappedConstraints: MutableMap<AmNode, MutableList<TView.Constraints.Items>> = HashMap()
        for (viewConstraint in tView.constraints) {
            if (!viewConstraint.items.isEmpty()) {
                val node: AmNode = AmUtils.resolvePath(root, viewConstraint.path)
                if (node != null) {
                    val itemsList = mappedConstraints.computeIfAbsent(
                        node
                    ) { k: AmNode? -> ArrayList() }
                    itemsList.addAll(viewConstraint.items)
                }
            }
        }
        return mappedConstraints
    }

    private fun convertBindingItems(cArchetypeRoot: CArchetypeRoot?): Map<String, Collection<TermBindingItem>>? {
        val result: MutableMap<String, Collection<TermBindingItem>>?
        if (cArchetypeRoot!!.termBindings != null && !cArchetypeRoot.termBindings.isEmpty()) {
            result = LinkedHashMap(cArchetypeRoot.termBindings.size)
            for (termBindingSet in cArchetypeRoot.termBindings) {
                var items: List<TermBindingItem>
                items = if (termBindingSet.items != null) {
                    ArrayList(termBindingSet.items)
                } else {
                    emptyList()
                }
                result[termBindingSet.terminology] = items
            }
        } else {
            result = null
        }
        return result
    }
}
