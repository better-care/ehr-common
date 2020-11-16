/* Copyright 2020-2025 Better Ltd (www.better.care)
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

import care.better.openehr.rm.RmObject
import care.better.platform.annotation.RequiresNotEmpty
import care.better.platform.annotation.RequiresNotNull
import com.google.common.base.CaseFormat
import java.lang.reflect.Field
import java.lang.reflect.Method
import java.lang.reflect.Modifier
import java.util.concurrent.ConcurrentHashMap

/**
 * @author Primoz Delopst
 */
@Suppress("UNCHECKED_CAST")
class RmUtils {
    companion object {
        private val CLASS_MAP: ConcurrentHashMap<String, ClassInfo> = ConcurrentHashMap()

        private val PACKAGE_NAMES: List<String> = listOf(
                "org.openehr.am.aom",
                "org.openehr.base.basetypes",
                "org.openehr.base.foundationtypes",
                "org.openehr.base.resource",
                "org.openehr.proc.taskplanning",
                "org.openehr.rm.common",
                "org.openehr.rm.composition",
                "org.openehr.rm.datastructures",
                "org.openehr.rm.datatypes",
                "org.openehr.rm.ehr",
                "org.openehr.rm.integration")

        @JvmStatic
        fun getRmClass(className: String): Class<out RmObject> = getClassInfo(className).clazz

        @JvmStatic
        fun getRmTypeName(clazz: Class<out RmObject>): String = CaseFormat.UPPER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, clazz.simpleName)

        @JvmStatic
        fun getAllFields(className: String) = getClassInfo(className).fields

        @JvmStatic
        fun getAllFields(clazz: Class<out RmObject>) = getClassInfo(clazz).fields

        @JvmStatic
        fun getRequiredFields(className: String) = getClassInfo(className).requiredFields

        @JvmStatic
        fun getRequiredFields(clazz: Class<out RmObject>) = getClassInfo(clazz).requiredFields

        @JvmStatic
        fun getFieldForAttribute(attributeName: String): String = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, attributeName)

        @JvmStatic
        fun getAttributeForField(fieldName: String): String = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, fieldName)

        @JvmStatic
        fun getGetterForAttribute(attributeName: String, clazz: Class<out RmObject>): Method? =
                getGetter(attributeName, { CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, it) }, clazz)

        @JvmStatic
        fun getGetterForField(fieldName: String, clazz: Class<out RmObject>): Method? =
                getGetter(fieldName, { CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, it) }, clazz)

        @JvmStatic
        fun getSetterForAttribute(attributeName: String, clazz: Class<out RmObject>): Method? =
                getSetter(attributeName, { CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, it) }, clazz)

        @JvmStatic
        fun getSetterForField(fieldName: String, clazz: Class<out RmObject>): Method? =
                getSetter(fieldName, { CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, it) }, clazz)

        private fun getGetter(name: String, nameTransformer: (String) -> String, clazz: Class<out RmObject>): Method? =
                with(getClassInfo(clazz).getter) {
                    val getMethod: Method? = this["get${nameTransformer.invoke(name)}"]
                    val setMethod: Method? = this["get${nameTransformer.invoke(name)}"]

                    if (getMethod != null || setMethod != null) {
                        getMethod ?: setMethod
                    } else {
                        null
                    }
                }

        private fun getSetter(name: String, nameTransformer: (String) -> String, clazz: Class<out RmObject>): Method? =
                getClassInfo(clazz).setter["set${nameTransformer.invoke(name)}"]


        private fun getClassInfo(name: String): ClassInfo =
                CLASS_MAP.computeIfAbsent(CaseFormat.UPPER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, name)) {
                    for (packageName in PACKAGE_NAMES) {
                        try {
                            val clazz = Class.forName("$packageName.${CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, name)}") as Class<out RmObject>
                            return@computeIfAbsent ClassInfo(
                                    clazz,
                                    getAllNonStaticFields(clazz),
                                    getAllRequiredFields(clazz),
                                    getGetters(clazz),
                                    getSetters(clazz))

                        } catch (ignore: ClassNotFoundException) {
                        }
                    }
                    throw ClassNotFoundException(name)
                }

        private fun getClassInfo(clazz: Class<out RmObject>): ClassInfo =
                CLASS_MAP.computeIfAbsent(CaseFormat.UPPER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, clazz.simpleName)) {
                    return@computeIfAbsent ClassInfo(
                            clazz,
                            getAllNonStaticFields(clazz),
                            getAllRequiredFields(clazz),
                            getGetters(clazz),
                            getSetters(clazz))
                }

        private fun getAllNonStaticFields(clazz: Class<out RmObject>): Collection<Field> =
                with(mutableListOf<Field>()) {
                    addFieldsRecursive(clazz, { !Modifier.isStatic(it.modifiers) }, this)
                    this.toList()
                }

        private fun getAllRequiredFields(clazz: Class<out RmObject>): Collection<Field> =
                with(mutableListOf<Field>()) {
                    addFieldsRecursive(
                            clazz,
                            { it.getAnnotation(RequiresNotNull::class.java) != null || it.getAnnotation(RequiresNotEmpty::class.java) != null },
                            this)
                    this.toList()
                }

        private fun addFieldsRecursive(clazz: Class<*>?, predicate: (Field) -> Boolean, list: MutableList<Field>) {
            if (clazz == null) {
                return
            }

            clazz.declaredFields.filter(predicate).forEach { list.add(it) }
            addFieldsRecursive(clazz.superclass, predicate, list)
        }

        private fun getGetters(clazz: Class<out RmObject>): Map<String, Method> =
                clazz.methods.filter { it.name.startsWith("get") || it.name.startsWith("is") }.associateBy { it.name }


        private fun getSetters(clazz: Class<out RmObject>): Map<String, Method> =
                clazz.methods.filter { it.name.startsWith("set") }.associateBy { it.name }
    }

    data class ClassInfo(
            val clazz: Class<out RmObject>,
            val fields: Collection<Field>,
            val requiredFields: Collection<Field>,
            val getter: Map<String, Method>,
            val setter: Map<String, Method>)
}