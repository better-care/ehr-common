package care.better.platform.json.jackson.better

import care.better.openehr.rm.RmObject
import care.better.platform.json.jackson.RmUtils
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.cfg.MapperConfig
import com.fasterxml.jackson.databind.introspect.AnnotatedField
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod

/**
 * @author Primoz Delopst
 */

class BetterPlatformPropertyNamingStrategy : PropertyNamingStrategy() {
    private val rmObjectClass: Class<RmObject> = RmObject::class.java

    override fun nameForField(config: MapperConfig<*>, field: AnnotatedField?, defaultName: String): String? =
            if (field != null && field.declaringClass != null && isRmClass(field.declaringClass))
                RmUtils.getAttributeForField(defaultName)
            else
                super.nameForField(config, field, defaultName)

    override fun nameForGetterMethod(config: MapperConfig<*>, method: AnnotatedMethod?, defaultName: String): String? =
            if (method != null && method.declaringClass != null && isRmClass(method.declaringClass))
                RmUtils.getAttributeForField(defaultName)
            else
                super.nameForGetterMethod(config, method, defaultName)

    override fun nameForSetterMethod(config: MapperConfig<*>, method: AnnotatedMethod?, defaultName: String): String? =
            if (method != null && method.declaringClass != null && isRmClass(method.declaringClass))
                RmUtils.getAttributeForField(defaultName)
            else
                super.nameForSetterMethod(config, method, defaultName)

    private fun isRmClass(declaringClass: Class<*>): Boolean = rmObjectClass.isAssignableFrom(declaringClass)
}
