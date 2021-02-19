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
import care.better.platform.utils.RmUtils
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

/**
 * @author Primoz Delopst
 * @since 3.1.0
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

    @Suppress("UNCHECKED_CAST")
    @Throws(IOException::class)
    override fun deserializeTypedFromObject(p: JsonParser, ctxt: DeserializationContext): Any? {
        var jsonParser = p
        if (jsonParser.canReadTypeId()) {
            val typeId = jsonParser.typeId
            if (typeId != null) {
                return _deserializeWithNativeTypeId(jsonParser, ctxt, typeId)
            }
        }
        var t = jsonParser.currentToken
        if (t == JsonToken.START_OBJECT) {
            t = jsonParser.nextToken()
        } else if (t != JsonToken.FIELD_NAME) {
            return _deserializeTypedUsingDefaultImpl(jsonParser, ctxt, null)
        }
        var tb: TokenBuffer? = null
        while (t == JsonToken.FIELD_NAME) {
            val name = jsonParser.currentName
            jsonParser.nextToken()
            if (name == _typePropertyName) {
                return _deserializeTypedForId(jsonParser, ctxt, tb)
            }
            if (tb == null) {
                tb = TokenBuffer(jsonParser, ctxt)
            }
            tb.writeFieldName(name)
            tb.copyCurrentStructure(jsonParser)
            t = jsonParser.nextToken()
        }
        if (tb != null) {
            tb.writeEndObject()
            jsonParser = tb.asParser(jsonParser)
            jsonParser.nextToken()
        }
        val rawClass: Class<*> = baseType().rawClass
        if (RmObject::class.java.isAssignableFrom(rawClass)) {
            if (tb != null) {
                tb.writeEndObject()
                jsonParser = tb.asParser(jsonParser)
                jsonParser.nextToken()
            }
            return deserializeTypedForId(jsonParser, ctxt, tb!!, RmUtils.getRmTypeName(rawClass as Class<out RmObject>))
        }
        return _deserializeTypedUsingDefaultImpl(jsonParser, ctxt, tb)
    }

    @Throws(IOException::class)
    fun deserializeTypedForId(p: JsonParser, context: DeserializationContext, tb: TokenBuffer, typeId: String): Any? {
        var jsonParser = p
        var tokenBuffer: TokenBuffer? = tb
        val deserializer: JsonDeserializer<Any> = _findDeserializer(context, typeId)
        if (_typeIdVisible) {
            if (tokenBuffer == null) {
                tokenBuffer = TokenBuffer(jsonParser, context)
            }

            tokenBuffer.writeFieldName(jsonParser.currentName)
            tokenBuffer.writeString(typeId)
        }
        if (tokenBuffer != null) {
            jsonParser.clearCurrentToken()
            jsonParser = JsonParserSequence.createFlattened(false, tokenBuffer.asParser(jsonParser), jsonParser)
        }
        jsonParser.nextToken()
        return deserializer.deserialize(jsonParser, context)
    }
}
