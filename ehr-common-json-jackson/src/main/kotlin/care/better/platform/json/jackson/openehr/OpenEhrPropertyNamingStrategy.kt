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

package care.better.platform.json.jackson.openehr

import care.better.platform.annotation.OpenEhrName
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.cfg.MapperConfig
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod
import org.openehr.rm.ehr.EhrStatus

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */

class OpenEhrPropertyNamingStrategy : PropertyNamingStrategies.SnakeCaseStrategy() {
    private val ehrStatusClass: Class<EhrStatus> = EhrStatus::class.java

    override fun nameForGetterMethod(config: MapperConfig<*>, method: AnnotatedMethod?, defaultName: String): String? =
        if (method != null && method.declaringClass != null && isRmClass(method.declaringClass))
            getOpenEhrName(method) ?: super.nameForSetterMethod(config, method, defaultName)
        else
            super.nameForGetterMethod(config, method, defaultName)

    override fun nameForSetterMethod(config: MapperConfig<*>, method: AnnotatedMethod?, defaultName: String): String? =
        if (method != null && method.declaringClass != null && isRmClass(method.declaringClass))
            getOpenEhrName(method) ?: super.nameForSetterMethod(config, method, defaultName)
        else
            super.nameForSetterMethod(config, method, defaultName)

    private fun getOpenEhrName(method: AnnotatedMethod): String? = method.getAnnotation(OpenEhrName::class.java)?.name

    private fun isRmClass(declaringClass: Class<*>): Boolean = ehrStatusClass.isAssignableFrom(declaringClass)
}
