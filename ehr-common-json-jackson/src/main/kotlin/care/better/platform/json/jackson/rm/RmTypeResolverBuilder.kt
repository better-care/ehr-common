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

package care.better.platform.json.jackson.rm

import care.better.openehr.rm.RmObject
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id
import com.fasterxml.jackson.databind.DeserializationConfig
import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping
import com.fasterxml.jackson.databind.SerializationConfig
import com.fasterxml.jackson.databind.cfg.MapperConfig
import com.fasterxml.jackson.databind.jsontype.*
import com.fasterxml.jackson.databind.jsontype.impl.AsPropertyTypeDeserializer
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */
class RmTypeResolverBuilder(defaultTyping: DefaultTyping) : ObjectMapper.DefaultTypeResolverBuilder(defaultTyping, LaissezFaireSubTypeValidator.instance) {

    override fun buildTypeSerializer(config: SerializationConfig, baseType: JavaType, subtypes: Collection<NamedType?>?): TypeSerializer? =
        when {
            _idType == Id.NONE -> null
            !useForType(baseType) -> null
            _includeAs == JsonTypeInfo.As.PROPERTY -> {
                val idRes: TypeIdResolver = idResolver(config, baseType, LaissezFaireSubTypeValidator.instance, subtypes, forSer = true, forDeser = false)
                RmObjectAsPropertyTypeSerializer(idRes, null, _typeProperty)
            }
            else -> super.buildTypeSerializer(config, baseType, subtypes)
        }

    override fun buildTypeDeserializer(config: DeserializationConfig?, baseType: JavaType?, subtypes: Collection<NamedType?>?): TypeDeserializer? =
        with(super.buildTypeDeserializer(config, baseType, subtypes)) {
            return if (this is AsPropertyTypeDeserializer) {
                RmAwareAsPropertyTypeDeserializer(this, null)
            } else this
        }

    override fun useForType(t: JavaType): Boolean =
        if (!RmObject::class.java.isAssignableFrom(t.rawClass) && t.rawClass != Any::class.java)
            false
        else super.useForType(t)


    override fun idResolver(
            config: MapperConfig<*>,
            baseType: JavaType,
            subtypeValidator: PolymorphicTypeValidator?,
            subtypes: Collection<NamedType?>?,
            forSer: Boolean,
            forDeser: Boolean): TypeIdResolver {
        if (_idType == Id.CLASS) {
            if (RmObject::class.java.isAssignableFrom(baseType.rawClass) || baseType.rawClass == Any::class.java) {
                return RmIdResolver(config.typeFactory)
            }
        }
        return super.idResolver(config, baseType, subtypeValidator, subtypes, forSer, forDeser)
    }
}
