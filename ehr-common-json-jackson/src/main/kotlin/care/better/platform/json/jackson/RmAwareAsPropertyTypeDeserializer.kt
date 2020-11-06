package care.better.platform.json.jackson

import care.better.openehr.rm.RmObject
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonToken
import com.fasterxml.jackson.core.util.JsonParserSequence
import com.fasterxml.jackson.databind.BeanProperty
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer
import com.fasterxml.jackson.databind.jsontype.impl.AsPropertyTypeDeserializer
import com.fasterxml.jackson.databind.util.TokenBuffer
import java.io.IOException
import kotlin.reflect.KClass

/**
 * @author Primoz Delopst
 */
class RmAwareAsPropertyTypeDeserializer(src: AsPropertyTypeDeserializer?, property: BeanProperty?) : AsPropertyTypeDeserializer(src, property) {

    @Throws(IOException::class)
    override fun _deserializeTypedUsingDefaultImpl(p: JsonParser, ctxt: DeserializationContext, tb: TokenBuffer?): Any? {
        var jsonParser = p

        val jsonDeserializer = ctxt.findRootValueDeserializer(ctxt.typeFactory.constructType(MutableMap::class.java))

        if (tb != null) {
            tb.writeEndObject()
            jsonParser = tb.asParser(jsonParser)
            jsonParser.nextToken()
        }
        return jsonDeserializer.deserialize(jsonParser, ctxt)
    }

    @Throws(IOException::class)
    override fun deserializeTypedFromAny(p: JsonParser, ctxt: DeserializationContext): Any? =
            if (p.currentToken == JsonToken.START_ARRAY)
                ctxt.findRootValueDeserializer(ctxt.typeFactory.constructType(MutableCollection::class.java)).deserialize(p, ctxt)
            else
                deserializeTypedFromObject(p, ctxt)


    override fun forProperty(prop: BeanProperty?): TypeDeserializer? {
        val typeDeserializer: TypeDeserializer = super.forProperty(prop)
        return if (typeDeserializer is AsPropertyTypeDeserializer)
            RmAwareAsPropertyTypeDeserializer(typeDeserializer, null)
        else
            typeDeserializer
    }

    @Throws(IOException::class)
    override fun deserializeTypedFromObject(p: JsonParser, ctxt: DeserializationContext): Any? {
        var p = p
        if (p.canReadTypeId()) {
            val typeId = p.typeId
            if (typeId != null) {
                return _deserializeWithNativeTypeId(p, ctxt, typeId)
            }
        }
        var t = p.currentToken
        if (t == JsonToken.START_OBJECT) {
            t = p.nextToken()
        } else if (t != JsonToken.FIELD_NAME) {
            return _deserializeTypedUsingDefaultImpl(p, ctxt, null)
        }
        var tb: TokenBuffer? = null
        while (t == JsonToken.FIELD_NAME) {
            val name = p.currentName
            p.nextToken()
            if (name == _typePropertyName) {
                return _deserializeTypedForId(p, ctxt, tb)
            }
            if (tb == null) {
                tb = TokenBuffer(p, ctxt)
            }
            tb.writeFieldName(name)
            tb.copyCurrentStructure(p)
            t = p.nextToken()
        }
        if (tb != null) {
            tb.writeEndObject()
            p = tb.asParser(p)
            // must move to point to the first token:
            p.nextToken()
        }
        val rawClass: Class<*> = baseType().getRawClass()
        if (RmObject::class.java.isAssignableFrom(rawClass)) {
            if (tb != null) {
                tb.writeEndObject()
                p = tb.asParser(p)
                // must move to point to the first token:
                p.nextToken()
            }
            return _deserializeTypedForId(p, ctxt, tb!!, RmUtils.getRmTypeName(rawClass.kotlin as KClass<out RmObject>))
        }
        return _deserializeTypedUsingDefaultImpl(p, ctxt, tb)
    }

    @Throws(IOException::class)
    fun _deserializeTypedForId(p: JsonParser, ctxt: DeserializationContext, tb: TokenBuffer, typeId: String): Any? {
        var p = p
        var tb: TokenBuffer? = tb
        val deser: JsonDeserializer<Any> = _findDeserializer(ctxt, typeId)
        if (_typeIdVisible) {
            if (tb == null) {
                tb = TokenBuffer(p, ctxt)
            }
            tb!!.writeFieldName(p.currentName)
            tb!!.writeString(typeId)
        }
        if (tb != null) {
            p.clearCurrentToken()
            p = JsonParserSequence.createFlattened(false, tb!!.asParser(p), p)
        }
        p.nextToken()
        return deser.deserialize(p, ctxt)
    }
}
