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

package care.better.platform.utils

import org.openehr.am.aom.*

/**
 * @author Primoz Delopst
 */
@Suppress("MemberVisibilityCanBePrivate")
object TemplateUtils {
    const val SEM_VER_PLACEHOLDER = "sem_ver"

    @JvmStatic
    fun findCStringPrimitive(cObjects: Collection<CObject>): CString? =
        cObjects.asSequence().filter { it is CPrimitiveObject && it.item is CString }.map { (it as CPrimitiveObject).item as CString }.firstOrNull()

    @JvmStatic
    fun findCCodePhrase(cObjects: Collection<CObject>): CCodePhrase? = cObjects.asSequence().filterIsInstance<CCodePhrase>().firstOrNull()

    @JvmStatic
    fun findAttribute(attributes: Collection<CAttribute>, rmAttributeName: String): CAttribute? =
        attributes.firstOrNull{ rmAttributeName == it.rmAttributeName }

    @JvmStatic
    fun findPath(cObject: CObject?, vararg attributes: String): CObject? {
        var obj = cObject
        for (attribute in attributes) {
            obj =
                if (obj is CComplexObject) {
                    val cAttribute = findAttribute(obj.attributes, attribute) ?: return null

                    if (cAttribute.children.size == 1)
                        cAttribute.children[0]
                    else
                        return null
                } else {
                    return null
                }
        }
        return obj
    }

    @JvmStatic
    fun findTerm(terms: Collection<ArchetypeTerm>, code: String): ArchetypeTerm? = terms.firstOrNull { it.code == code }

    @JvmStatic
    fun findDictionaryItem(archetypeTerm: ArchetypeTerm, id: String): String? =
        archetypeTerm.items.asSequence().filter { it.id == id }.map { it.value }.firstOrNull()

    @JvmStatic
    fun findTerm(termDefinitions: Collection<ArchetypeTerm>, nodeId: String, id: String): String? =
         findTerm(termDefinitions, nodeId)?.let { findDictionaryItem(it, id) }

    @JvmStatic
    fun findTemplateLanguages(template: Template): List<String> {
        val languages: MutableList<String> = mutableListOf()
        template.language?.codeString?.also { languages.add(it) }

        val termDefinitions = template.ontology?.termDefinitions
        if (termDefinitions != null) {
            for (ontologyDefinition in termDefinitions) {
                if (!languages.contains(ontologyDefinition.language)) {
                    languages.add(ontologyDefinition.language)
                }
            }
        }
        return languages.toList()
    }

    @JvmStatic
    fun extractSemVerFromTemplateDescription(template: Template): String? =
        template.description?.otherDetails?.asSequence()?.filter { it.id == SEM_VER_PLACEHOLDER }?.map { it.value }?.firstOrNull()

    @JvmStatic
    fun <T> findPrimitive(valueChild: CObject?, clazz: Class<T>): T? {
        if (valueChild is CPrimitiveObject) {
            if (clazz.isInstance(valueChild.item)) {
                return clazz.cast(valueChild.item)
            }
        }
        return null
    }
}
