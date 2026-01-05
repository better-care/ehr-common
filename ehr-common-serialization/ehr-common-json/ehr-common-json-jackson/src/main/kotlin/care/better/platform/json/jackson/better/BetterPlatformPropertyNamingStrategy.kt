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

package care.better.platform.json.jackson.better

import care.better.openehr.rm.RmObject
import care.better.platform.utils.RmUtils
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.cfg.MapperConfig
import com.fasterxml.jackson.databind.introspect.AnnotatedField
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod

/**
 * @author Primoz Delopst
 * @since 3.1.0
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
