package care.better.platform.web.template.converter.json.serializers

import care.better.platform.time.format.OpenEhrDateTimeFormatter
import care.better.platform.time.temporal.OpenEhrTemporal
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonToken
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.jsontype.TypeSerializer
import com.fasterxml.jackson.databind.ser.std.StdSerializer

/**
 * @author Matic Ribic
 */
class OpenEhrSerializer() : StdSerializer<OpenEhrTemporal<*>>(OpenEhrTemporal::class.java) {

    override fun serialize(value: OpenEhrTemporal<*>?, generator: JsonGenerator, provider: SerializerProvider) {
        requireNotNull(value) { handledType().simpleName }
        generator.writeString(OpenEhrDateTimeFormatter.ofPattern("", false).format(value))
    }

    override fun serializeWithType(value: OpenEhrTemporal<*>, generator: JsonGenerator, provider: SerializerProvider, typeSerializer: TypeSerializer) {
        val typeId = typeSerializer.typeId(value, JsonToken.VALUE_STRING)
        typeSerializer.writeTypePrefix(generator, typeId);
        serialize(value, generator, provider);
        typeSerializer.writeTypeSuffix(generator, typeId);
    }
}