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

@file:JvmName("AmUtils")

package care.better.platform.template

import care.better.platform.path.PathSegment
import care.better.platform.path.PathUtils
import care.better.platform.template.exception.AmException
import org.openehr.am.aom.*
import org.openehr.base.foundationtypes.IntervalOfInteger
import org.openehr.rm.common.Locatable
import org.openehr.rm.datatypes.DvCodedText
import org.openehr.rm.datatypes.DvText
import java.util.*
import java.util.regex.Pattern

/**
 * @author Bostjan Lah
 * @author Primoz Delopst
 * @since 3.1.0
 */
@Suppress("MemberVisibilityCanBePrivate")
object AmUtils {
    const val VALUE_ATTRIBUTE = "value"
    const val NAME_ATTRIBUTE = "name"
    const val CODE_PHRASE_ATTRIBUTE = "defining_code"
    const val TEXT_ID = "text"
    const val DESCRIPTION_ID = "description"
    val NAME_SUFFIX: Pattern = Pattern.compile("\\s*#[0-9]+\\s*$")

    @JvmStatic
    fun createInterval(lower: Int, upper: Int?) =
        IntervalOfInteger().apply {
            this.lower = lower
            this.upper = upper
            this.lowerIncluded = true
            this.upperIncluded = true
        }

    @JvmStatic
    fun getAmNode(amNode: AmNode, vararg pathSegments: String): AmNode? =
        with(getAmNodesRecursively(listOf(amNode), pathSegments.toList(), 0)) {
            if (this.isEmpty()) null else this.iterator().next()
        }

    @JvmStatic
    fun getAmNodes(amNode: AmNode, vararg pathSegments: String): List<AmNode> = getAmNodesRecursively(listOf(amNode), pathSegments.toList(), 0)


    private fun getAmNodesRecursively(amNodes: List<AmNode>, pathSegments: List<String>, index: Int): List<AmNode> {
        if (amNodes.isEmpty()) {
            return emptyList()
        }

        val pathSegment = pathSegments[index]
        val amNode = getAmNodeWithAttribute(amNodes, pathSegment)
        val children =  amNode?.attributes?.get(pathSegment)?.getChildren() ?: emptyList()
        return if (index == pathSegments.size -1) children else getAmNodesRecursively(children, pathSegments, index + 1)
    }

    @JvmStatic
    fun getAmNodeWithAttribute(amNodes: Collection<AmNode>, attributeName: String): AmNode? =
        amNodes.firstOrNull { it.attributes.containsKey(attributeName) }

    @JvmStatic
    fun findTerm(terms: Collection<ArchetypeTerm>, code: String?): ArchetypeTerm? = terms.firstOrNull { it.code == code }

    @JvmStatic
    fun findTerm(terms: Collection<ArchetypeTerm>, nodeId: String?, id: String): String? = findTerm(terms, nodeId)?.let { findDictionaryItem(it, id) }

    @JvmStatic
    fun findDictionaryItem(archetypeTerm: ArchetypeTerm, id: String): String? =
        archetypeTerm.items.asSequence().filter { id == it.id }.map { it.value }.firstOrNull()

    @JvmStatic
    fun findText(amNode: AmNode, language: String, archetypeNodeId: String): String? = findTermText(amNode, language, archetypeNodeId, TEXT_ID)

    @JvmStatic
    fun findDescription(amNode: AmNode, language: String, archetypeNodeId: String): String? = findTermText(amNode, language, archetypeNodeId, DESCRIPTION_ID)

    @JvmStatic
    fun findTermText(amNode: AmNode, archetypeNodeId: String): String? = amNode.getTerms()?.let { findTerm(it, archetypeNodeId, TEXT_ID) }

    private fun findTermText(amNode: AmNode, language: String, archetypeNodeId: String, id: String): String? {
        val termDefinitions = amNode.getTermDefinitions()
        return when{
            termDefinitions != null && termDefinitions.containsKey(language) -> findTerm(termDefinitions[language] ?: emptyList(),archetypeNodeId,  id)
            language == amNode.getTemplateLangugage() -> findTerm(amNode.getTerms() ?: emptyList(), archetypeNodeId, id)
            else -> null
        }
    }

    @JvmStatic
    fun findTermBindings(amNode: AmNode, nodeId: String?): Map<String, TermBindingItem>? =
        amNode.getTermBindings()?.let {
            val map: LinkedHashMap<String, TermBindingItem> = linkedMapOf()
            it.forEach { (key, value) -> findTermBindings(nodeId, value)?.also { term -> map[key] = term } }
            map
        }

    private fun findTermBindings(nodeId: String?, bindings: Collection<TermBindingItem>) =
        bindings.firstOrNull { Objects.equals(nodeId, it.code) }

    @JvmStatic
    fun <T : CPrimitive> getPrimitiveItem(amNode: AmNode, clazz: Class<T>, vararg pathSegments: String): T? =
        getAmNodes(amNode, *pathSegments)
            .firstOrNull { it.cObject is CPrimitiveObject && it.cObject.item != null && clazz.isInstance(it.cObject.item) }
            ?.let { clazz.cast((it.cObject as CPrimitiveObject).item) }

    @JvmStatic
    fun <T : CObject> getCObjectItem(amNode: AmNode, clazz: Class<T>, vararg pathSegments: String): T? =
        getAmNodes(amNode, *pathSegments).firstOrNull { clazz.isInstance(it.cObject) }?.let { clazz.cast(it.cObject) }

    @JvmStatic
    fun <T : CObject> getCObjectItems(amNode: AmNode, clazz: Class<T>, vararg pathSegments: String): List<T> =
        getAmNodes(amNode, *pathSegments).asSequence().filter {  clazz.isInstance(it.cObject)  }.map { clazz.cast(it.cObject) }.toList()

    @JvmStatic
    fun getMin(interval: IntervalOfInteger?): Int? =
        if (interval != null && !interval.lowerUnbounded) {
            if (false == interval.lowerIncluded) interval.lower?.plus(1) else interval.lower
        } else {
            null
        }

    @JvmStatic
    fun getMax(interval: IntervalOfInteger?): Int? =
        if (interval != null && !interval.upperUnbounded) {
            if (false == interval.upperIncluded) interval.upper?.plus(1) else interval.upper
        } else {
            null
        }

    @JvmStatic
    fun attributeNameOf(parent: AmNode, child: AmNode): String? = parent.attributes.entries.firstOrNull { it.value.getChildren().contains(child) }?.key

    @JvmStatic
    fun attributeOf(parent: AmNode, child: AmNode): AmAttribute? = parent.attributes.entries.firstOrNull { it.value.getChildren().contains(child) }?.value

    @JvmStatic
    fun getAttributeNames(amNode: AmNode, from: Int, to: Int): List<String?> =
        getInRange(amNode, from, to) { parent, child -> attributeNameOf(parent, child) }


    @JvmStatic
    fun getParents(amNode: AmNode,  from: Int, to: Int): List<AmNode> =
        getInRange(amNode, from, to) { parent, _ -> parent }


    private fun <T> getInRange(amNode: AmNode, from: Int, to: Int, function: (AmNode, AmNode) -> T): List<T> {
        val list = mutableListOf<T>()
        var node: AmNode? = amNode
        var index = 0
        while (node != null && index <= to) {
            val parent: AmNode? = node.parent
            if (index >= from && parent != null) {
                list.add(function.invoke(parent, node))
            }
            node = parent
            index++
        }
        return list.toList()
    }

    @JvmStatic
    fun getOptOnlyAttributes(amNode: AmNode): List<AmAttribute> = amNode.attributes.values.filter { !it.isRmOnly() }

    @JvmStatic
    fun isNameConstrained(amNode: AmNode): Boolean = amNode.attributes[NAME_ATTRIBUTE].let { it != null && !it.isRmOnly() }

    @JvmStatic
    fun nameMatches(am: AmNode, name: DvText): Boolean = !isNameConstrained(am) || constrainedNameMatches(am, name)

    @JvmStatic
    fun getNameCodePhrase(amNode: AmNode): CCodePhrase? =
        getAmNode(amNode, NAME_ATTRIBUTE, CODE_PHRASE_ATTRIBUTE)?.let { if (it.cObject is CCodePhrase) it.cObject else null }


    private fun nameMatches(am: AmNode, locatable: Locatable): Boolean = nameMatches(am, locatable.name ?: throw AmException("Locatable name is required."))

    private fun nameMatches(amNode: AmNode, name: String): Boolean {
        if (name != amNode.name) {
            val nameCodePhrase = getNameCodePhrase(amNode)
            return if (nameCodePhrase == null) {
                false
            } else {
                val codeList: List<String> = nameCodePhrase.codeList
                codeList.isEmpty() || codeList.stream().anyMatch { name == findTermText(amNode, it) }
            }
        }
        return true
    }

    private fun constrainedNameMatches(amNode: AmNode, name: DvText): Boolean {
        val nameCodePhrase = getNameCodePhrase(amNode)
        if (nameCodePhrase == null) {
            val rmName = name.value
            val amName: String? = amNode.name
            return if (rmName != amName && amName != null && rmName != null) NAME_SUFFIX.matcher(rmName).replaceAll("") == amName else true
        } else {
            if (name is DvCodedText) {
                val nameCodePhraseTerminologyId = nameCodePhrase.terminologyId
                if (nameCodePhraseTerminologyId != null && nameCodePhrase.terminologyId?.value?.isNotBlank() == true) {
                    val terminologyId = name.definingCode?.terminologyId?.value
                    if (!Objects.equals(terminologyId, nameCodePhraseTerminologyId.value)) {
                        return false
                    }
                } else {
                    return nameCodePhrase.codeList.isEmpty() || nameCodePhrase.codeList.contains(name.definingCode?.codeString)
                }
            }
            return false
        }
    }

    @JvmStatic
    fun archetypeMatches(amNode: AmNode, locatable: Locatable): Boolean {
        if (amNode.archetypeNodeId != null && amNode.archetypeNodeId != amNode.nodeId) {
            if (amNode.archetypeNodeId != locatable.archetypeNodeId) {
                val archetypeDetails = locatable.archetypeDetails
                return archetypeDetails?.archetypeId != null && amNode.archetypeNodeId == archetypeDetails.archetypeId?.value
            }
        } else {
            return amNode.nodeId == null || locatable.archetypeNodeId != null && amNode.nodeId == locatable.archetypeNodeId
        }
        return true
    }

    @JvmStatic
    fun matches(am: AmNode, locatable: Locatable): Boolean = archetypeMatches(am, locatable) && nameMatches(am, locatable)

    @JvmStatic
    fun resolvePath(amNode: AmNode, path: String): AmNode? = resolvePathRecursively(amNode, PathUtils.getPathSegments(path), 0)

    private fun resolvePathRecursively(amNode: AmNode, pathSegments: List<PathSegment>, index: Int): AmNode? {
        if (index > pathSegments.size - 1) {
            return amNode
        }
        val pathSegment = pathSegments[index]

        val amAttribute = amNode.attributes[pathSegment.element] ?: return null

        val name = pathSegment.name
        val archetypeNodeId = pathSegment.archetypeNodeId

        val constrainedAmNode = name?.let {
            amAttribute.getChildren()
                .firstOrNull { child -> isNameConstrained(child) && segmentMatches(child, archetypeNodeId, NAME_SUFFIX.matcher(name).replaceAll("")) }
        }
        return if (constrainedAmNode == null) {
            val child = amAttribute.getChildren().firstOrNull { segmentMatches(it, archetypeNodeId, name) }
            if (child == null)
                null
            else
                resolvePathRecursively(child, pathSegments, index + 1)
        } else {
            resolvePathRecursively(constrainedAmNode, pathSegments, index + 1)
        }
    }

    @JvmStatic
    fun segmentMatches(node: AmNode, segmentArchetypeNodeId: String?, segmentName: String?): Boolean =
         (segmentArchetypeNodeId == null || segmentArchetypeNodeId == node.archetypeNodeId) &&
                (segmentName == null || !isNameConstrained(node) || nameMatches(node, NAME_SUFFIX.matcher(segmentName).replaceAll("")))

    @JvmStatic
    fun findMatchingNode(amNodes: Iterable<AmNode>, locatable: Locatable): AmNode? {
        var matchingNode: AmNode? = null
        for (amNode in amNodes) {
            if (archetypeMatches(amNode, locatable)) {
                if (nameMatches(amNode, locatable)) {
                    if (locatable.name!!.value == amNode.name)
                        return amNode
                    else
                        matchingNode = amNode
                } else if (matchingNode == null) {
                    matchingNode = amNode
                }
            }
        }
        return matchingNode
    }
}
