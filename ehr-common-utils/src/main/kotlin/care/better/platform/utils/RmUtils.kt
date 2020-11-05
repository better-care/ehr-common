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

@file:JvmName("RmUtils")

package care.better.platform.utils

import care.better.openehr.rm.RmObject
import care.better.platform.annotation.Required
import care.better.platform.utils.exception.RmClassCastException
import care.better.platform.utils.exception.RmClassFieldNotFoundException
import com.google.common.base.CaseFormat
import java.lang.reflect.*
import java.util.concurrent.ConcurrentHashMap

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */
@Suppress("UNCHECKED_CAST", "unused")
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
        @Throws(RmClassCastException::class)
        fun getRmClass(className: String): Class<out RmObject> = getClassInfo(className).clazz

        @JvmStatic
        fun getRmTypeName(clazz: Class<*>): String = CaseFormat.UPPER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, clazz.simpleName)

        @JvmStatic
        @Throws(RmClassCastException::class)
        fun getAllFields(className: String) = getClassInfo(className).fields

        @JvmStatic
        @Throws(RmClassCastException::class)
        fun getAllFields(clazz: Class<out RmObject>) = getClassInfo(clazz).fields

        @JvmStatic
        @Throws(RmClassCastException::class)
        fun getRequiredFields(className: String) = getClassInfo(className).requiredFields

        @JvmStatic
        @Throws(RmClassCastException::class)
        fun getRequiredFields(clazz: Class<out RmObject>) = getClassInfo(clazz).requiredFields

        @JvmStatic
        fun getFieldForAttribute(attributeName: String): String = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, attributeName)

        @JvmStatic
        fun getAttributeForField(fieldName: String): String = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, fieldName)

        @JvmStatic
        @Throws(RmClassCastException::class)
        fun getGetterForAttribute(attributeName: String, clazz: Class<out RmObject>): Method? =
            getGetter(attributeName, { CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, it) }, clazz)

        @JvmStatic
        @Throws(RmClassCastException::class)
        fun getGetterForField(fieldName: String, clazz: Class<out RmObject>): Method? =
            getGetter(fieldName, { CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, it) }, clazz)

        @JvmStatic
        @Throws(RmClassCastException::class)
        fun getSetterForAttribute(attributeName: String, clazz: Class<out RmObject>): Method? =
            getSetter(attributeName, { CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, it) }, clazz)

        @JvmStatic
        @Throws(RmClassCastException::class)
        fun getSetterForField(fieldName: String, clazz: Class<out RmObject>): Method? =
            getSetter(fieldName, { CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, it) }, clazz)

        @JvmStatic
        @Throws(RmClassCastException::class, RmClassFieldNotFoundException::class)
        fun getFieldType(clazz: Class<out RmObject>, fieldName: String) =
            getClassInfo(clazz).fieldTypes[fieldName] ?: throw RmClassFieldNotFoundException(clazz.name, fieldName)

        @JvmStatic
        fun isRmClass(clazz: Class<*>): Boolean = PACKAGE_NAMES.contains(clazz.`package`.name)

        private fun getGetter(name: String, nameTransformer: (String) -> String, clazz: Class<out RmObject>): Method? =
            with(getClassInfo(clazz).getter) {
                val getMethod: Method? = this["get${nameTransformer.invoke(name)}"]
                val setMethod: Method? = this["is${nameTransformer.invoke(name)}"]

                if (getMethod != null || setMethod != null) {
                    getMethod ?: setMethod
                } else {
                    null
                }
            }

        private fun getSetter(name: String, nameTransformer: (String) -> String, clazz: Class<out RmObject>): Method? =
            getClassInfo(clazz).setter["set${nameTransformer.invoke(name)}"]


        @Throws(RmClassCastException::class)
        private fun getClassInfo(name: String): ClassInfo =
            with(getNonGenericRmNamePart(name)) {
                CLASS_MAP.computeIfAbsent(CaseFormat.UPPER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, this)) {
                    for (packageName in PACKAGE_NAMES) {
                        try {
                            val clazz = Class.forName("$packageName.${CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, this)}") as Class<out RmObject>
                            return@computeIfAbsent ClassInfo(
                                clazz,
                                getAllNonStaticFields(clazz),
                                getAllRequiredFields(clazz),
                                getFieldTypes(clazz),
                                getGetters(clazz),
                                getSetters(clazz))

                        } catch (ignore: ClassNotFoundException) {
                        }
                    }
                    throw RmClassCastException(name)
                }
            }

        private fun getClassInfo(clazz: Class<out RmObject>): ClassInfo =
            CLASS_MAP.computeIfAbsent(CaseFormat.UPPER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, clazz.simpleName)) {
                return@computeIfAbsent ClassInfo(
                    clazz,
                    getAllNonStaticFields(clazz),
                    getAllRequiredFields(clazz),
                    getFieldTypes(clazz),
                    getGetters(clazz),
                    getSetters(clazz))
            }

        fun getNonGenericRmNamePart(name: String): String =
            with(name.indexOf('<')) {
                if (this == -1)
                    name
                else
                    name.substring(0, this)
            }

        fun getGenericRmNamePart(name: String): String =
            with(name.indexOf('<')) {
                if (this == -1)
                    name
                else
                    name.substring(this + 1, if (name.last() == '>') name.length - 1 else name.length)
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
                    { it.getAnnotation(Required::class.java) != null },
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


        private fun getFieldTypes(clazz: Class<out RmObject>): Map<String, Class<*>> =
            with(mutableListOf<Field>()) {
                addFieldsRecursive(clazz, { true }, this)
                this.associate { Pair(it.name, getParametrizedClass(it.genericType) ?: it.type) }
            }

        private fun getParametrizedClass(type: Type?): Class<*>? {
            if (type is ParameterizedType) {
                return with(type.actualTypeArguments) {
                    if (this.isNotEmpty()) {
                        if (this[0] is Class<*>) {
                            this[0] as Class<*>
                        } else {
                            getParametrizedClass(this[0])
                        }
                    } else {
                        null
                    }
                }
            }
            return null
        }
    }

    data class ClassInfo(
            val clazz: Class<out RmObject>,
            val fields: Collection<Field>,
            val requiredFields: Collection<Field>,
            val fieldTypes: Map<String, Class<*>>,
            val getter: Map<String, Method>,
            val setter: Map<String, Method>)
}
