package care.better.platform.json.jackson

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
 */
class RmTypeResolverBuilder(defaultTyping: DefaultTyping) : ObjectMapper.DefaultTypeResolverBuilder(defaultTyping, LaissezFaireSubTypeValidator.instance) {

    override fun buildTypeSerializer(config: SerializationConfig, baseType: JavaType, subtypes: Collection<NamedType?>?): TypeSerializer? =
            when {
                _idType == Id.NONE -> null
                !useForType(baseType) -> null
                _includeAs == JsonTypeInfo.As.PROPERTY -> {
                    val idRes: TypeIdResolver = idResolver(config, baseType, LaissezFaireSubTypeValidator.instance, subtypes, true, false)
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
