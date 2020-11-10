@file:Suppress("UNCHECKED_CAST")

package care.better.platform.json.jackson

import care.better.openehr.rm.RmObject
import com.google.common.base.CaseFormat
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KClass

/**
 * @author Primoz Delopst
 */
class RmUtils {
    companion object {
        private val CLASS_MAP: ConcurrentHashMap<String, KClass<out RmObject>> = ConcurrentHashMap()
        private val PACKAGE_NAMES: List<String> = listOf(
                "org.openehr.am.aom",
                "org.openehr.base.basetype",
                "org.openehr.base.foundationtypes",
                "org.openehr.base.resource",
                "org.openehr.proc.taskplanning",
                "org.openehr.rm.common",
                "org.openehr.rm.composition",
                "org.openehr.rm.datastructures",
                "org.openehr.rm.datatypes",
                "org.openehr.rm.ehr",
                "org.openehr.rm.integration")

        fun getRmClass(name: String): KClass<out RmObject> = CLASS_MAP.computeIfAbsent(name) { findClass(name) }

        fun getRmTypeName(clazz: KClass<out RmObject>): String = CaseFormat.UPPER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, clazz.simpleName!!)

        fun getAttributeForField(fieldName: String): String = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, fieldName)

        private fun findClass(name: String): KClass<out RmObject> {
            for (packageName in PACKAGE_NAMES) {
                try {
                    return Class.forName("$packageName.${CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, name)}").kotlin as KClass<out RmObject>
                } catch (ignore: ClassNotFoundException) {
                }
            }
            throw ClassNotFoundException(name)
        }
    }
}