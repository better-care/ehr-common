package care.better.platform.json.jackson.openehr

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.cfg.MapperConfig
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod
import kotlinx.serialization.SerialName
import org.openehr.rm.ehr.EhrStatus

/**
 * @author Primoz Delopst
 */

class OpenEhrPropertyNamingStrategy : PropertyNamingStrategy.SnakeCaseStrategy() {
    private val ehrStatusClass: Class<EhrStatus> = EhrStatus::class.java

    override fun nameForGetterMethod(config: MapperConfig<*>, method: AnnotatedMethod?, defaultName: String): String? =
            if (method != null && method.declaringClass != null && isRmClass(method.declaringClass))
                getSerialName(method) ?: super.nameForSetterMethod(config, method, defaultName)
            else
                super.nameForGetterMethod(config, method, defaultName)

    override fun nameForSetterMethod(config: MapperConfig<*>, method: AnnotatedMethod?, defaultName: String): String? =
            if (method != null && method.declaringClass != null && isRmClass(method.declaringClass))
                getSerialName(method) ?: super.nameForSetterMethod(config, method, defaultName)
            else
                super.nameForSetterMethod(config, method, defaultName)

    private fun getSerialName(method: AnnotatedMethod): String? = method.getAnnotation(SerialName::class.java)?.value

    private fun isRmClass(declaringClass: Class<*>): Boolean = ehrStatusClass.isAssignableFrom(declaringClass)
}
